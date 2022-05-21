package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    FacadeUtilisateur facadeUtilisateur;

    @PostMapping
    public ResponseEntity<Utilisateur> creerUtilisateur(@RequestBody Utilisateur utilisateur) {

//        Utilisateur utilisateur = new Utilisateur("Admin","Admin", Role.PersonnelAdm);
//        facadeUtilisateur.save(utilisateur);
//        return ResponseEntity.ok().build();

        if (utilisateur.getRole().equals(Role.PersonnelAdm)) {
            if (utilisateur.getLogin() == null || utilisateur.getLogin().isEmpty() ||
                    utilisateur.getPassword() == null || utilisateur.getPassword().isEmpty() ||
                    utilisateur.getRole() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                if (facadeUtilisateur.findUtilisateurByLogin(utilisateur.getLogin()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                } else {
                    Utilisateur user = new Utilisateur(utilisateur.getLogin(), utilisateur.getPassword(), Role.PersonnelAdm);
                    facadeUtilisateur.save(user);
                    return ResponseEntity.ok().body(user);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
