package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    @Autowired
    FacadeUtilisateur facadeUtilisateur;

    @PostMapping
    public ResponseEntity<Utilisateur> creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur("Admin","Admin", Role.PersonnelAdm);
        facadeUtilisateur.save(utilisateur);
        return ResponseEntity.ok().build();
    }
}
