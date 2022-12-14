package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Groupe {
    @Id
    private String nomG;
    private List<Etudiant> etudiants;
    private List<SousModule> sousModules;

    public Groupe(String nomG, List<Etudiant> etudiants, List<SousModule> sousModule) {
        this.nomG = nomG;
        this.etudiants = etudiants;
        this.sousModules = sousModule;
    }

    public Groupe(String nomG, List<Etudiant> etudiants) {
        this.nomG = nomG;
        this.etudiants = etudiants;
        this.sousModules = List.of();
    }

    public Groupe() {
        this.etudiants = List.of();
        this.sousModules = List.of();
    }

    public List<SousModule> getSousModules() {
        return sousModules;
    }

    public void setSousModules(List<SousModule> sousModules) {
        this.sousModules = sousModules;
    }

    public void addSousModule(SousModule sousModule) {
        this.sousModules.add(sousModule);
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public void addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
    }

    public String getNomG() {
        return nomG;
    }

    public void setNomG(String nomG) {
        this.nomG = nomG;
    }
}
