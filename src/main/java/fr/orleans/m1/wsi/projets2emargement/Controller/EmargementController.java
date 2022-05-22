package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.*;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
        if (em.getHeureDebut() == null || em.getHeureFin() == null || em.getSalle().getNomSalle() == null || em.getSalle().getNomSalle().isEmpty() || em.getSousModule().getNomSM() == null || em.getSousModule().getNomSM().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else if (facadeEmargement.findByHeureDebutAndHeureFinAndSalle(em.getHeureDebut(), em.getHeureFin(), facadeSalle.findById(em.getSalle().getNomSalle()).get()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else {
            if (facadeSalle.findById(em.getSalle().getNomSalle()).isEmpty() || facadeSousModule.findById(em.getSousModule().getNomSM()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (em.getHeureDebut().isAfter(em.getHeureFin())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            } else {
                SousModule sm = facadeSousModule.findById(em.getSousModule().getNomSM()).get();
                Salle s = facadeSalle.findById(em.getSalle().getNomSalle()).get();
                Emargement emm = new Emargement(em.getHeureDebut(), em.getHeureFin(), sm, s, facadeGroupe.findById(sm.getGroupe()).get().getEtudiants());
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
     * Emargement d'un étudiant si le principal si l'utilisateur est un étudiant
     * Cloture d'un émargement par un enseignant si l'utilisateur est un enseignant
     *
     * @param idEmargement
     * @param principal
     * @return
     */
    @PutMapping("/{idEmargement}")
    public ResponseEntity<String> emarger(@PathVariable String idEmargement, Principal principal) {

        Utilisateur utilisateur = facadeUtilisateur.findUtilisateurByLogin(principal.getName()).get();
        Optional<Emargement> emargement = facadeEmargement.findById(idEmargement);

        if (emargement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            if (utilisateur.getRole().equals(Role.Etudiant)) {
                Etudiant etudiant = facadeEtudiant.findById(principal.getName()).get();
                if (emargement.get().getEtudiantsAbsents().contains(etudiant)
                        && !emargement.get().getEtudiantsPresents().contains(etudiant)) {
                    emargement.get().supprimerEtudiant(etudiant);
                    emargement.get().addEtudiantsPresents(etudiant);
                    //return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Emargement enregistré avec succès pour l'étudiant: " + etudiant.getNumEtu());
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            } else {
                if (utilisateur.getRole().equals(Role.Enseignant)) {
                    Enseignant enseignant = facadeEnseignant.findById(principal.getName()).get();
                    if (emargement.get().getSousModule().getEnseignant().equals(enseignant)
                            && emargement.get().getEtatEmargement().equals(EtatEmargement.Ouvert)) {
                        emargement.get().setEtatEmargement(EtatEmargement.Clos);
                        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
                    } else {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }

                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        }
    }

    /**
     * Liste de tous les émargements ouverts
     *
     * @return
     */
    @GetMapping("/ouverts")
    public ResponseEntity<List<Emargement>> getEmargementsOuverts() {
//            List<Emargement> listeEmargementsOuverts = new ArrayList<>();
//            facadeEmargement.findByEtatEmargement("Ouvert").forEach(e -> listeEmargementsOuverts.add(e.get()));
//            return ResponseEntity.ok().body(listeEmargementsOuverts);
        return ResponseEntity.ok().body(facadeEmargement.findByEtatEmargement(EtatEmargement.Ouvert));
    }

    /**
     * Liste de tous les émargements clos
     *
     * @return
     */
    @GetMapping("/clos")
    public ResponseEntity<List<Emargement>> getEmargementsClos() {
//            List<Emargement> listeEmargementsClos = new ArrayList<>();
//            facadeEmargement.findByEtatEmargement("Clos").forEach(e -> listeEmargementsClos.add(e.get()));
//            return ResponseEntity.ok().body(listeEmargementsClos);
        return ResponseEntity.ok().body(facadeEmargement.findByEtatEmargement(EtatEmargement.Clos));
    }

    /**
     * Consultation de l'émargement
     *
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}")
    public ResponseEntity<Emargement> getEmargement(@PathVariable String idEmargement) {
        Optional<Emargement> emargement = facadeEmargement.findById(idEmargement);
        if (emargement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(emargement.get());
        }
    }

    /**
     * Liste de tous les étudiants presents
     *
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}/present")
    public ResponseEntity<List<Etudiant>> getEmargementEtudiantPresent(@PathVariable String idEmargement) {
        Optional<Emargement> emargement = facadeEmargement.findById(idEmargement);
        if (emargement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(emargement.get().getEtudiantsPresents());
        }
    }

    /**
     * Liste de tous les étudiants absents
     *
     * @param idEmargement
     * @return
     */
    @GetMapping("/{idEmargement}/absent")
    public ResponseEntity<List<Etudiant>> getEtudiantsAbsentEmargement(@PathVariable String idEmargement) {
        Optional<Emargement> emargement = facadeEmargement.findById(idEmargement);
        if (emargement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(emargement.get().getEtudiantsAbsents());
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
    @GetMapping(value = "/QR/{idEmargement}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> getQR(@PathVariable("idEmargement") String idEmargement, Principal principal) throws Exception {
        Optional<Emargement> emargement = facadeEmargement.findById(idEmargement);
        if (emargement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(QRCodeGenerator.generateQRCodeImage("http://localhost:8080/emargement/" + idEmargement + "/" + MD5(principal.getName())));
        }

    }

    /**
     * Fonction de cryptage de type MD5
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static String MD5(String s) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, m.digest()).toString(16);
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
    @GetMapping("/{id}/{idUtilisateur}")
    public ResponseEntity<Emargement> ExecuteQR(@PathVariable("id") String id, @PathVariable("idUtilisateur") String idUtilisateur, Principal principal) throws Exception {
        if (Objects.equals(idUtilisateur, MD5(principal.getName()))) {
            Emargement em = facadeEmargement.findById(id).get();
            Utilisateur utilisateur = facadeUtilisateur.findUtilisateurByLogin(principal.getName()).get();
            if (utilisateur.getRole().equals(Role.Etudiant)) {
                em.addEtudiantsPresents(facadeEtudiant.findEtudiantByEmail(principal.getName()).get());
                em.supprimerEtudiant(facadeEtudiant.findEtudiantByEmail(principal.getName()).get());
            } else if (utilisateur.getRole().equals(Role.Enseignant)) {
                em.setEtatEmargement(EtatEmargement.Clos);
            }
            facadeEmargement.save(em);
            return ResponseEntity.ok().body(em);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Recuperer tous les emargements
     *
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<Emargement>> getAll() {
        if (facadeEmargement.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(facadeEmargement.findAll());
        }
    }

    /**
     * Recuperer tous les emargements clos où l'étudiant est absent
     *
     * @return
     */
    @GetMapping("/{idEtu}")
    public ResponseEntity<List<Emargement>> getEmargementEtudiantAbsent(@PathVariable("idEtu") String idEtudiant) {

        Optional<Etudiant> etudiant = facadeEtudiant.findById(idEtudiant);

        if (etudiant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<Emargement> emargement = facadeEmargement.findByEtatEmargement(EtatEmargement.Clos);
            List<Emargement> emargementsEtudiantAbsent = new ArrayList<>();
            for (Emargement e : emargement) {
                if (e.getEtudiantsAbsents().contains(etudiant)) {
                    emargementsEtudiantAbsent.add(e);
                }
            }
            return ResponseEntity.ok().body(emargementsEtudiantAbsent);
        }
    }
}



