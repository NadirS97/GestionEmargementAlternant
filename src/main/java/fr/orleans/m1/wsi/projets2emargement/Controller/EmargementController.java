package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEmargement;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeGroupe;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSalle;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSousModule;
import fr.orleans.m1.wsi.projets2emargement.Modele.Emargement;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    FacadeGroupe facadeGroupe;
    @PostMapping("/")
    public ResponseEntity<Emargement> addEmg(@RequestBody Emargement em) {
        if(em.getHeureDebut() == null || em.getHeureFin() == null || em.getSalle().getNomSalle() == null || em.getSalle().getNomSalle().isEmpty() || em.getSousModule().getNomSM() == null || em.getSousModule().getNomSM().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
                Emargement emm = new Emargement(em.getHeureDebut(),em.getHeureFin(),sm,em.getSalle(),facadeGroupe.findById(sm.getGroupe()).get().getEtudiants());
                facadeEmargement.save(emm);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(emm.getIdEmargement())
                        .toUri();
                return ResponseEntity.created(location).body(emm);
            }
        }
    }

}
