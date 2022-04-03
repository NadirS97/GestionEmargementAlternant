package fr.orleans.m1.wsi.projets2emargement.Modele;

import java.util.List;

public class Enseignant {
    private String nomEns;
    private String prenomEns;
    private String idEnseignant;
    private List<SousModule> sousModules;

    public Enseignant(String nomEns, String prenomEns, String idEnseignant) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.sousModules=List.of();

    }

    public Enseignant(String nomEns, String prenomEns, String idEnseignant, List<SousModule> sousModules) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.sousModules = sousModules;
    }

    public String getNomEns() {
        return nomEns;
    }

    public void setNomEns(String nomEns) {
        this.nomEns = nomEns;
    }

    public String getPrenomEns() {
        return prenomEns;
    }

    public void setPrenomEns(String prenomEns) {
        this.prenomEns = prenomEns;
    }

    public String getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(String idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public List<SousModule> getSousModules() {
        return sousModules;
    }

    public void setSousModules(List<SousModule> sousModules) {
        this.sousModules = sousModules;
    }
}
