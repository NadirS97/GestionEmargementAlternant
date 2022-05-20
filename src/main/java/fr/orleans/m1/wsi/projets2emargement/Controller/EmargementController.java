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
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Emargement d'un étudiant
     * @param idEmargement
     * @param principal
     * @return
     */
    @PutMapping("/{idEmargement}")
    public ResponseEntity<String> emargerEtudiant(@PathVariable String idEmargement, Principal principal) {

        Etudiant etudiant = facadeEtudiant.findById(principal.getName()).get();
        Emargement emargement = facadeEmargement.findById(idEmargement).get();

        if (emargement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            if (emargement.getEtudiantsAbsents().contains(etudiant)
                    && !emargement.getEtudiantsPresents().contains(etudiant)
                    && etudiant.getEtat().equals(Etat.ABSENT)) {
                etudiant.setEtat(Etat.PRESENT);
                emargement.addEtudiantsPresents(etudiant);
                //return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Emargement enregistré avec succès pour l'étudiant: " + etudiant.getNumEtu());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

    }

    /**
     * Cloture d'un émargement par un enseignant
     * @param idEmargement
     * @param principal
     * @return
     */
    @PutMapping("/{idEmargement}/cloture")
    public ResponseEntity<String> clotureEmargementEnseignant(@PathVariable String idEmargement, Principal principal) {

        Enseignant enseignant = facadeEnseignant.findById(principal.getName()).get();
        Emargement emargement = facadeEmargement.findById(idEmargement).get();

        if (emargement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            if (emargement.getSousModule().getEnseignant().equals(enseignant)
                    && emargement.getEtatEmargement().equals(EtatEmargement.Ouvert)) {
                emargement.setEtatEmargement(EtatEmargement.Clos);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }

    /**
     * Liste de tous les émargements ouverts
     * @return
     */
    @GetMapping("/ouverts")
    public ResponseEntity<List<Emargement>> getEmargementsOuverts(){
        List<Emargement> listeEmargementsOuverts = new ArrayList<>();
        facadeEmargement.findByEtatEmargement("Ouvert").forEach(e -> listeEmargementsOuverts.add(e.get()));
        return ResponseEntity.ok().body(listeEmargementsOuverts);
    }

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


//    @GetMapping("/clos")
//    public ResponseEntity<List<Emargement>> getEmargementsOuverts(){
//        List<Emargement> listeEmargementsOuverts = new ArrayList<>();
//        for (Optional<Emargement> emargement : facadeEmargement.findEmargementsByHeureDebutBeforeAndHeureFinAfter(LocalDateTime.now(), LocalDateTime.now())) {
//            if(emargement.isPresent()){
//                listeEmargementsOuverts.add(emargement.get());
//            }else{
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//        }
//        return ResponseEntity.ok(listeEmargementsOuverts);
//    }

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

    /**
     * Liste de tous les étudiants presents
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}/present")
    public ResponseEntity<List<Etudiant>> getEmargementEtudiantPresent(@PathVariable String idEmargement){
        Emargement emargement = facadeEmargement.findById(idEmargement).get();
        List<Etudiant> listeEtudiantsPresent = emargement.getEtudiantsPresents();
        if (emargement == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok().body(listeEtudiantsPresent);
        }
    }

    /**
     * Liste de tous les étudiants absents
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}/absent")
    public ResponseEntity<List<Etudiant>> getEmargementEtudiantAbsent(@PathVariable String idEmargement){
        Emargement emargement = facadeEmargement.findById(idEmargement).get();
        List<Etudiant> listeEtudiantsAbsent = emargement.getEtudiantsPresents();
        if (emargement == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok().body(listeEtudiantsAbsent);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> getQR(@PathVariable("id") String id,String idEtu) throws Exception {
        return ResponseEntity.ok(QRCodeGenerator.generateQRCodeImage("http://localhost:8080/emargement/168fd113-5370-4a35-8046-1df0f051c1b8/o23312"));
    }

    @GetMapping( "/{id}/{idEtu}")
    public ResponseEntity<Emargement> ExecuteQR(@PathVariable("id") String id,@PathVariable("idEtu") String idEtu) {
        Emargement em = facadeEmargement.findById(id).get();
        em.addEtudiantsPresents(facadeEtudiant.findById(idEtu).get());
        facadeEmargement.save(em);
        return ResponseEntity.ok().body(em);
    }

}
