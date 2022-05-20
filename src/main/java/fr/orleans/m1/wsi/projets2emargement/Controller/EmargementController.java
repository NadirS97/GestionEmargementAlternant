package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.*;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/emargement")
public class EmargementController {

    @Autowired
    FacadeEmargement facadeEmargement;
    @Autowired
    FacadeSousModule facadeSousModule;
    @Autowired
    FacadeSalle facadeSalle;
    @Autowired
    FacadeEtudiant facadeEtudiant;
    @Autowired
    FacadeGroupe facadeGroupe;
    @Autowired
    FacadeEnseignant facadeEnseignant;
    @Autowired
    FacadeUtilisateur facadeUtilisateur;

    /**
     * Creation d'un nouvel émargement
     *
     * @param em
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<Emargement> creerEmargement(@RequestBody Emargement em) {
        if(em.getHeureDebut() == null || em.getHeureFin() == null || em.getSalle().getNomSalle() == null || em.getSalle().getNomSalle().isEmpty() || em.getSousModule().getNomSM() == null || em.getSousModule().getNomSM().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(facadeSalle.findById(em.getSalle().getNomSalle()).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else  if (facadeEmargement.findByHeureDebutAndHeureFinAndSalle(em.getHeureDebut(),em.getHeureFin(),facadeSalle.findById(em.getSalle().getNomSalle()).get()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else{
            if(facadeSalle.findById(em.getSalle().getNomSalle()).isEmpty() || facadeSousModule.findById(em.getSousModule().getNomSM()).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (em.getHeureDebut().isAfter(em.getHeureFin())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }else{
                SousModule sm= facadeSousModule.findById(em.getSousModule().getNomSM()).get();
                Salle s= facadeSalle.findById(em.getSalle().getNomSalle()).get();
                Emargement emm = new Emargement(em.getHeureDebut(),em.getHeureFin(),sm,s,facadeGroupe.findById(sm.getGroupe()).get().getEtudiants());
                facadeEmargement.save(emm);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(emm.getIdEmargement())
                        .toUri();
                return ResponseEntity.created(location).body(emm);
            }
        }
    }

//    @GetMapping("/clos")
//    public ResponseEntity<Emargement[]> getEmargementClos(){
//
//    }

    /**
     * Liste de tous les émargements clos
     * @return
     */
    @GetMapping("/clos")
    public ResponseEntity<List<Emargement>> getEmargementsClos(){
        List<Emargement> listeEmargementsClos = new ArrayList<>();
        facadeEmargement.findByEtatEmargement("Clos").forEach(e -> listeEmargementsClos.add(e.get()));
        return ResponseEntity.ok().body(listeEmargementsClos);
    }

    /**
     * Consultation de l'émargement
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}")
    public ResponseEntity<Emargement> getEmargement(@PathVariable String idEmargement){
        Emargement emargement = facadeEmargement.findById(idEmargement).get();
        if(emargement == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok().body(emargement);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> getQR(@PathVariable("id") String id,String idEtu) throws Exception {
        return ResponseEntity.ok(QRCodeGenerator.generateQRCodeImage("http://localhost:8080/emargement/168fd113-5370-4a35-8046-1df0f051c1b8/o23312"));
    }

    /**
     * Liste de tous les étudiants absents
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}/absent")
    public ResponseEntity<List<Etudiant>> getEmargementEtudiantAbsent(@PathVariable String idEmargement){
        Emargement emargement = facadeEmargement.findById(idEmargement).get();
        if (emargement == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok().body(emargement.getEtudiantsAbsents());
        }
    }

    /**
     * Recupere un QR Code pour un emargement
     *
     * @param idEmargement
     * @param principal
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{idEmargement}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> getQR(@PathVariable("idEmargement") String idEmargement, Principal principal) throws Exception {
        return ResponseEntity.ok(QRCodeGenerator.generateQRCodeImage("http://localhost:8080/emargement/"+idEmargement+"/"+MD5(principal.getName())));
    }

    /**
     * Fonction de cryptage de type MD5
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static String MD5(String s) throws Exception {
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        return new BigInteger(1,m.digest()).toString(16);
    }

    /**
     * Fonction qui permet de scanner un QR Code
     *
     * @param id
     * @param idUtilisateur
     * @param principal
     * @return
     * @throws Exception
     */
    @GetMapping( "/{id}/{idUtilisateur}")
    public ResponseEntity<Emargement> ExecuteQR(@PathVariable("id") String id,@PathVariable("idUtilisateur") String idUtilisateur, Principal principal) throws Exception {
        if(Objects.equals(idUtilisateur, MD5(principal.getName()))){
            Emargement em = facadeEmargement.findById(id).get();
            Utilisateur utilisateur = facadeUtilisateur.findUtilisateurByLogin(principal.getName()).get();
            if(utilisateur.getRole().equals(Role.Etudiant)) {
                em.addEtudiantsPresents(facadeEtudiant.findEtudiantByEmail(principal.getName()).get());
                em.supprimerEtudiant(facadeEtudiant.findEtudiantByEmail(principal.getName()).get());
            }else if(utilisateur.getRole().equals(Role.Enseignant)){
                em.setEtatEmargement(EtatEmargement.Clos);
            }
            facadeEmargement.save(em);
            return ResponseEntity.ok().body(em);
        }else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Recuperer tous les emargements
     *
     * @return
     */
    @GetMapping( "/")
    public ResponseEntity<List<Emargement>> getAll() {
        return ResponseEntity.ok().body(facadeEmargement.findAll());
    }

}
