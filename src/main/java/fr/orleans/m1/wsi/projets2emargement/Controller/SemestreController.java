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
@RequestMapping("/semestre")
public class SemestreController {

    @Autowired
    private FacadeSemestre facadeSemestre;

    @Autowired
    private FacadeModule facadeModule;

    @GetMapping("/")
    public ResponseEntity<List<Semestre>> getAllSemestre(){
        return ResponseEntity.ok().body(facadeSemestre.findAll());
    }

    @GetMapping("/{nomSemestre}")
    public ResponseEntity<Semestre> getSemestreByName(@PathVariable("nomSemestre") String nomSemestre){
        Optional<Semestre> semestre=facadeSemestre.findById(nomSemestre);
        return semestre.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Semestre> creerSemestre(@RequestBody Semestre semestre){

        if(semestre.getNomS()==null || semestre.getNomS().isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            if (facadeSemestre.findById(semestre.getNomS()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                Semestre s = new Semestre(semestre.getNomS());
                facadeSemestre.save(s);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                        .path("/{nomSemestre}")
                                                        .buildAndExpand(s.getNomS())
                                                        .toUri();
                return ResponseEntity.created(location).body(s);
            }
        }
    }

    @DeleteMapping("/{nomSemestre}")
    public ResponseEntity<String> supprimerSemestre(@PathVariable("nomSemestre") String nomSemestre) {
        Optional<Semestre> semestre = facadeSemestre.findById(nomSemestre);
        if (semestre.isPresent()) {
            for(Optional<Module> m : facadeModule.findBySemestre(nomSemestre)){
                m.get().setSemestre("");
                facadeModule.save(m.get());
            }
            facadeSemestre.deleteById(nomSemestre);
            return ResponseEntity.ok("Le semestre a bien ??t?? supprim??.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le semestre est introuvable.");
        }
    }

}
