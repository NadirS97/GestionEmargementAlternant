package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeModule;
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
@RequestMapping("/Mod")
public class ModuleContoller {
    @Autowired
    private FacadeModule facadeModule;

    @GetMapping("/")
    public ResponseEntity<List<Module>> GetAll(){
        return ResponseEntity.ok().body(facadeModule.findAll());
    }

    @GetMapping("/{CodeMod}")
    public ResponseEntity<Module> GetModuleByCode(@PathVariable("CodeMod") String CodeMod){
        Optional<Module> module=facadeModule.findById(CodeMod);
        return module.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Module> CreateModule(@RequestBody Module module){
        facadeModule.save(module);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{CodeMod}").buildAndExpand(module.getCode()).toUri();
        return ResponseEntity.created(location).body(module);
    }

    @DeleteMapping("/{CodeMod}")
    public ResponseEntity<String> DeleteModule(@PathVariable("CodeMod") String CodeMod) {
        Optional<Module> module = facadeModule.findById(CodeMod);
        if (module.isPresent()) {
            facadeModule.deleteById(CodeMod);
            return ResponseEntity.ok("Element bien suprimee");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element non trouvable");
        }
    }



}
