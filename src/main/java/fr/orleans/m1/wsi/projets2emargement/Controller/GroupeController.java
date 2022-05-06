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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/groupe")
public class GroupeController {
    @Autowired
    private FacadeGroupe facadeGroupe;

    @Autowired
    private FacadeEtudiant facadeEtudiant;

    @GetMapping("/")
    public ResponseEntity<List<Groupe>> GetAll(){
        return ResponseEntity.ok().body(facadeGroupe.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Groupe> CreateSemestre(@RequestBody Groupe groupe){
        if(groupe.getNomG()==null || groupe.getNomG().isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            if(facadeGroupe.findById(groupe.getNomG()).isPresent())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else {
                Groupe g = new Groupe(groupe.getNomG(),new ArrayList<Etudiant>());
                for (Etudiant e: groupe.getEtudiants()){
                    if (facadeEtudiant.findById(e.getNumEtu()).isPresent()){
                        Etudiant et = facadeEtudiant.findById(e.getNumEtu()).get();
                        et.addGroupe(g.getNomG());
                        facadeEtudiant.save(et);
                        g.addEtudiant(et);
                    }
                }
                facadeGroupe.save(g);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{NomG}")
                        .buildAndExpand(g.getNomG())
                        .toUri();

                return ResponseEntity.created(location).body(g);
            }
        }
    }

    @GetMapping("/{NomG}")
    public ResponseEntity<Groupe> GetSemestreByName(@PathVariable("NomG") String nomG){
        Optional<Groupe> g=facadeGroupe.findById(nomG);
        return g.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
