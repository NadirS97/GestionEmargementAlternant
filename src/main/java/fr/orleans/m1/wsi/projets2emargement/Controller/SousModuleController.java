package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.*;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import fr.orleans.m1.wsi.projets2emargement.Modele.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SMod")
public class SousModuleController {

    @Autowired
    FacadeSousModule facadeSousModule;
    @Autowired
    private FacadeModule facadeModule;
    @Autowired
    private FacadeEnseignant facadeEnseignant;
    @Autowired
    private FacadeGroupe facadeGroupe;

    @GetMapping("/")
    public ResponseEntity<List<SousModule>> getAllSousModule(){
        return ResponseEntity.ok().body(facadeSousModule.findAll());
    }

    @GetMapping("/{NomSMod}")
    public ResponseEntity<SousModule> getModuleByCode(@PathVariable("NomSMod") String nomSMod){
        Optional<SousModule> Sousmodule=facadeSousModule.findById(nomSMod);
        return Sousmodule.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<SousModule> creerModule(@RequestBody SousModule sousModule){


        if(     sousModule.getNomSM()==null || sousModule.getNomSM().isEmpty() ||
                sousModule.getModule()==null || sousModule.getModule().isEmpty() ||
                sousModule.getTypeCours()==null || sousModule.getTypeCours().toString().isEmpty() ||
                sousModule.getEnseignant()==null || sousModule.getEnseignant().toString().isEmpty() ||
                sousModule.getGroupe()==null || sousModule.getGroupe().isEmpty()
        )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            if (facadeSousModule.findById(sousModule.getNomSM()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else if(facadeModule.findById(sousModule.getModule()).isEmpty()
                        || facadeEnseignant.findById(sousModule.getEnseignant()).isEmpty()
                        || facadeGroupe.findById(sousModule.getGroupe()).isEmpty()
                    ){
                         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else{
                Module m = facadeModule.findById(sousModule.getModule()).get();
                Enseignant e = facadeEnseignant.findById(sousModule.getEnseignant()).get();
                Groupe g = facadeGroupe.findById(sousModule.getGroupe()).get();
                SousModule sm = new SousModule(sousModule.getNomSM(),m.getNomM(), TypeCours.valueOf(sousModule.getTypeCours().toString()),g.getNomG(),e.getIdEnseignant());
                m.addSousModule(sm);
                g.addSousModule(sm);
                e.addSousModule(sm);
                facadeModule.save(m);
                facadeEnseignant.save(e);
                facadeGroupe.save(g);
                facadeSousModule.save(sm);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{NomSMod}")
                        .buildAndExpand(sm.getNomSM())
                        .toUri();
                return ResponseEntity.created(location).body(sm);

            }
        }


    }

//    @DeleteMapping("/{NomSMod}")
//    public ResponseEntity<String> DeleteModule(@PathVariable("CodeMod") String CodeMod) {
//        Optional<Module> module = facadeModule.findById(CodeMod);
//        if (module.isPresent()) {
//            facadeModule.deleteById(CodeMod);
//            return ResponseEntity.ok("Element bien suprime");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element non trouvable");
//        }
//    }
//
//    @PutMapping("/{NomSMod}")
//    public ResponseEntity<Module> ModifyModule(@PathVariable("CodeMod") String CodeMod,@RequestBody Module m) {
//        Optional<Module> module = facadeModule.findById(CodeMod);
//        if (module.isPresent()) {
//            if( m.getNomM()==null || m.getNomM().isEmpty() ||
//                    m.getSemestre()==null || m.getSemestre().isEmpty()){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//            }else{
//                module.get().setNomM(m.getNomM());
//                module.get().setSemestre(m.getSemestre());
//                facadeModule.save(module.get());
//                return ResponseEntity.ok(module.get());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }




}
