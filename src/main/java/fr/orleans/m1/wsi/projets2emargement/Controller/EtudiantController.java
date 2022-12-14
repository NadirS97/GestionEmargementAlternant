package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEtudiant;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
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
@RequestMapping("/etudiant")
public class EtudiantController {

   // private static List<Etudiant> etudiants= new ArrayList<>();
    @Autowired
    private FacadeEtudiant facadeEtudiant;
    @Autowired
    private FacadeUtilisateur facadeUtilisateur;

    @GetMapping("/")
    public ResponseEntity<List<Etudiant>> getAllEtudiant(){
        return ResponseEntity.ok().body(facadeEtudiant.findAll());
    }

    @GetMapping("/{NumEtu}")
    public ResponseEntity<Etudiant> getEtudiantByNumEtudiant(@PathVariable("NumEtu") String numEtu){
        Optional<Etudiant> etudiant =facadeEtudiant.findById(numEtu);
        return etudiant.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Etudiant> creerEtudiant(@RequestBody Etudiant etudiant){

        if (    etudiant.getNumEtu()==null || etudiant.getNumEtu().isEmpty()
                || etudiant.getNom()==null || etudiant.getNom().isEmpty()
                || etudiant.getPrenom()==null || etudiant.getPrenom().isEmpty()
            ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            if (facadeEtudiant.findById(etudiant.getNumEtu()).isPresent() || facadeEtudiant.findEtudiantByEmail(etudiant.getPrenom()+"."+etudiant.getNom()+"@etu.univ-orleans.fr").isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else{

                Etudiant e = new Etudiant(etudiant.getNumEtu(),etudiant.getNom(),etudiant.getPrenom(),(etudiant.getGroupes()==null||etudiant.getGroupes().isEmpty())?List.of():etudiant.getGroupes());
                facadeEtudiant.save(e);
                Utilisateur u = new Utilisateur(e.getEmail(), e.getNumEtu(), Role.Etudiant);
                facadeUtilisateur.save(u);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{NumEtu}").buildAndExpand(e.getNumEtu()).toUri();
                return ResponseEntity.created(location).body(e);
            }
        }
    }

    @DeleteMapping("/{NumEtu}")
    public ResponseEntity<String> supprimerEtudiant(@PathVariable("NumEtu") String numEtu) {
        Optional<Etudiant> etudiant = facadeEtudiant.findById(numEtu);
        if (etudiant.isPresent()) {
            facadeEtudiant.deleteById(numEtu);
            return ResponseEntity.ok("L'??tudiant a bien ??t?? supprim??.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'??tudiant est introuvable.");
        }
    }

}
