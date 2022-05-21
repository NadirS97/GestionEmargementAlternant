package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import fr.orleans.m1.wsi.projets2emargement.Modele.TypeCours;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataTestImpl implements DataTest {

    @Override
    public List<Etudiant> getEtudiants() {
        return Arrays.asList(
                new Etudiant("1", "Richard", "Jean", Arrays.asList("Groupe1")),
                new Etudiant("2", "TASDEMiR", "Seyma"),
                new Etudiant("3", "Saiah", "Nadir", Arrays.asList("Groupe2", "Groupe1"))
        );
    }

    @Override
    public Salle getSalle() {
        return new Salle("E01");
    }

    @Override
    public SousModule getSousModule() {
        return new SousModule("SM1", "Module1", TypeCours.CM, "Groupe1", "o123");
    }

    @Override
    public String getLoginAdmin() {
        return "Admin";
    }

    @Override
    public String getPasswordAdmin() {
        return "Admin";
    }


}
