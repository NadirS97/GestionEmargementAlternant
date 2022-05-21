package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;

import java.util.List;

public interface DataTest {

    List<Etudiant> getEtudiants();
    Salle getSalle();
    SousModule getSousModule();
    String getLoginAdmin();
    String getPasswordAdmin();

}
