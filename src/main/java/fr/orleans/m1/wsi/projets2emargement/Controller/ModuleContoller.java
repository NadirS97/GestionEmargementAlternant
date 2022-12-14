package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeModule;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSemestre;
import fr.orleans.m1.wsi.projets2emargement.Modele.Module;
import fr.orleans.m1.wsi.projets2emargement.Modele.Semestre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/module")
public class ModuleContoller {
    @Autowired
    private FacadeModule facadeModule;
    @Autowired
    private FacadeSemestre facadeSemestre;

    @GetMapping("/")
    public ResponseEntity<List<Module>> getAllModule(){
        return ResponseEntity.ok().body(facadeModule.findAll());
    }

    @GetMapping("/{CodeMod}")
    public ResponseEntity<Module> getModuleByCode(@PathVariable("CodeMod") String CodeMod){
        Optional<Module> module=facadeModule.findById(CodeMod);
        return module.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Module> creerModule(@RequestBody Module module){
        if(     module.getNomM()==null || module.getNomM().isEmpty() ||
                module.getCode()==null || module.getCode().isEmpty() ||
                module.getSemestre()==null || module.getSemestre().isEmpty()
            )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            if (facadeModule.findById(module.getCode()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else if(facadeSemestre.findById(module.getSemestre()).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else{
                Semestre s = facadeSemestre.findById(module.getSemestre()).get();
                Module m = new Module(module.getCode(),module.getNomM(),s.getNomS());
                s.addModule(m);
                facadeModule.save(m);
                facadeSemestre.save(s);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{CodeMod}")
                        .buildAndExpand(m.getCode())
                        .toUri();
                return ResponseEntity.created(location).body(m);

            }
        }

    }

    @DeleteMapping("/{CodeMod}")
    public ResponseEntity<String> supprimerModule(@PathVariable("CodeMod") String CodeMod) {
        Optional<Module> module = facadeModule.findById(CodeMod);
        if (module.isPresent()) {
            facadeModule.deleteById(CodeMod);
            return ResponseEntity.ok("Le module a bien ??t?? supprim??.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le module est introuvable.");
        }
    }

    @PutMapping("/{CodeMod}")
    public ResponseEntity<Module> modifierModule(@PathVariable("CodeMod") String CodeMod,@RequestBody Module m) {
        Optional<Module> module = facadeModule.findById(CodeMod);
        if (module.isPresent()) {
           if( m.getNomM()==null || m.getNomM().isEmpty() ||
            m.getSemestre()==null || m.getSemestre().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }else{
                module.get().setNomM(m.getNomM());
                module.get().setSemestre(m.getSemestre());
                facadeModule.save(module.get());
                return ResponseEntity.ok(module.get());
           }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
