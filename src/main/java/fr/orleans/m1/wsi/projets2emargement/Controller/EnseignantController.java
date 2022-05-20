package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEnseignant;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSousModule;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Enseignant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enseignant")
public class EnseignantController {

    @Autowired
    private FacadeEnseignant facadeEnseignant;
    @Autowired
    private FacadeSousModule facadeSousModule;
    @Autowired
    private FacadeUtilisateur facadeUtilisateur;

    @GetMapping("/")
    public ResponseEntity<List<Enseignant>> getAllEnseignant(){
        return ResponseEntity.ok().body(facadeEnseignant.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Enseignant> creerEnseignant(@RequestBody Enseignant ens){
        if(facadeEnseignant.findById(ens.getIdEnseignant()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            if(ens.getNomEns() == null || ens.getNomEns().isEmpty()
                || ens.getIdEnseignant() == null || ens.getIdEnseignant().isEmpty()
                || ens.getPrenomEns() == null || ens.getPrenomEns().isEmpty())
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else{

                    Enseignant enseignant = new Enseignant(ens.getNomEns(),ens.getPrenomEns(),ens.getIdEnseignant());
                    facadeEnseignant.save(enseignant);
                    Utilisateur u = new Utilisateur(enseignant.getEmail(), enseignant.getNomEns(), Role.Enseignant);
                    facadeUtilisateur.save(u);
                    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEns}").buildAndExpand(ens.getIdEnseignant()).toUri();
                    return ResponseEntity.created(uri).body(enseignant);
            }
        }
    }

    @GetMapping("/{idEns}")
    public ResponseEntity<Enseignant> getEnseignant(@PathVariable String idEns){
        Optional<Enseignant> ens = facadeEnseignant.findById(idEns);
        return ens.map(enseignant -> ResponseEntity.ok().body(enseignant)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{idEns}")
    public ResponseEntity<String> supprimerEnseignant(@PathVariable String idEns){
        Optional<Enseignant> ens = facadeEnseignant.findById(idEns);
        return ens.map(enseignant -> {

            for(Optional<SousModule> sm : facadeSousModule.findAllByEnseignant(ens.get())){
                (sm.get()).setEnseignant(null);
            }
            facadeEnseignant.deleteById(idEns);
            return ResponseEntity.ok().body("L'enseignant a bien été supprimé.");
        }).orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'enseignant est introuvable."));
    }

}
