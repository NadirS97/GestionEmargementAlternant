package fr.orleans.m1.wsi.projets2emargement.Controller;

import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataTestImpl implements DataTest {

    @Override
    public List<Etudiant> getEtudiants() {
        return Arrays.asList(
                new Etudiant("1", "Richard", "Jean", Arrays.asList("Groupe1")),
                new Etudiant("2", "TASDEMiR", "Seyma", Arrays.asList("Groupe1")),
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

    @Override
    public Groupe getGroupe() {
        return new Groupe("Groupe1", getEtudiants(), List.of(getSousModule()));
    }


}
