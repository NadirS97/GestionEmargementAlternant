package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEtudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Etu")
public class EtudiantController {

   // private static List<Etudiant> etudiants= new ArrayList<>();
    @Autowired
    private FacadeEtudiant facadeEtudiant;

    @GetMapping("/")
    public ResponseEntity<List<Etudiant>> GetAll(){
        return ResponseEntity.ok().body(facadeEtudiant.findAll());
    }

    @GetMapping("/{NumEtu}")
    public ResponseEntity<Etudiant> GetEtudiantByNumEtu(@PathVariable("NumEtu") String numEtu){
        Optional<Etudiant> etudiant=facadeEtudiant.findById(numEtu);
        return etudiant.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Etudiant> CreateEtudiant(@RequestBody Etudiant etudiant){
        etudiant.getUtilisateur().setPassword(etudiant.getUtilisateur().getPassword());
        facadeEtudiant.save(etudiant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{NumEtu}").buildAndExpand(etudiant.getNumEtu()).toUri();
        return ResponseEntity.created(location).body(etudiant);
    }

    @DeleteMapping("/{NumEtu}")
    public ResponseEntity<String> DeleteEtudiant(@PathVariable("NumEtu") String numEtu) {
        Optional<Etudiant> etudiant = facadeEtudiant.findById(numEtu);
        if (etudiant.isPresent()) {
            facadeEtudiant.deleteById(numEtu);
            return ResponseEntity.ok("Element bien suprimee");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element non trouvable");
        }
    }

}
