package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSalle;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salle")
public class SalleController  {
    @Autowired
    FacadeSalle facadeSalle;

    @GetMapping("/")
    public ResponseEntity<List<Salle>> GetAll(){
        return ResponseEntity.ok().body(facadeSalle.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Salle> CreateModule(@RequestBody Salle salle){

        if(salle.getNomSalle()==null || salle.getNomSalle().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            if (facadeSalle.findById(salle.getNomSalle().trim()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else{
                Salle s = new Salle(salle.getNomSalle());
                facadeSalle.save(s);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{salle}")
                        .buildAndExpand(s.getNomSalle())
                        .toUri();
                return ResponseEntity.created(location).body(s);
            }
        }
    }

    @GetMapping("/{salle}")
    public ResponseEntity<Salle> GetSalle(@PathVariable("salle") String salle){
        Optional<Salle> s=facadeSalle.findById(salle);
        return s.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
