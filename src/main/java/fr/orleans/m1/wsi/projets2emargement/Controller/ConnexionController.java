package fr.orleans.m1.wsi.projets2emargement.Controller;


import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEtudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Connexion")
public class ConnexionController {

    @Autowired
    private FacadeEtudiant facadeEtudiant;




}
