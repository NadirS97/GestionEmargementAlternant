package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEnseignant;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEtudiant;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSousModule;
import fr.orleans.m1.wsi.projets2emargement.Modele.Enseignant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Ens")
public class EnseignatController {

    @Autowired
    private FacadeEnseignant facadeEnseignant;
    @Autowired
    private FacadeSousModule facadeSousModule;

    @GetMapping("/")
    public ResponseEntity<List<Enseignant>> GetAll(){
        return ResponseEntity.ok().body(facadeEnseignant.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Enseignant> AddEns(@RequestBody Enseignant ens){
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
                    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEns}").buildAndExpand(ens.getIdEnseignant()).toUri();
                    return ResponseEntity.created(uri).body(enseignant);
            }
        }
    }

    @GetMapping("/{idEns}")
    public ResponseEntity<Enseignant> GetEns(@PathVariable String idEns){
        Optional<Enseignant> ens = facadeEnseignant.findById(idEns);
        return ens.map(enseignant -> ResponseEntity.ok().body(enseignant)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{idEns}")
    public ResponseEntity<String> DeleteEns(@PathVariable String idEns){
        Optional<Enseignant> ens = facadeEnseignant.findById(idEns);
        return ens.map(enseignant -> {

            for(Optional<SousModule> sm : facadeSousModule.findAllByEnseignant(ens.get())){
                (sm.get()).setEnseignant(null);
            }
            facadeEnseignant.deleteById(idEns);
            return ResponseEntity.ok().body("Element a ete bien suprimee");
        }).orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element non trouvable"));
    }

}
