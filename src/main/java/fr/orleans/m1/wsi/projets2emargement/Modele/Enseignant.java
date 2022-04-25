package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Enseignant {
    @Id
    private String idEnseignant;
    private String nomEns;
    private String prenomEns;
    private List<SousModule> sousModules;
    private Utilisateur utilisateur;

    public Enseignant(String nomEns, String prenomEns, String idEnseignant) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.sousModules=List.of();
        this.utilisateur = new Utilisateur(nomEns, idEnseignant, Role.Enseignant);

    }

    public Enseignant(String nomEns, String prenomEns, String idEnseignant, List<SousModule> sousModules) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.sousModules = sousModules;
        this.utilisateur = new Utilisateur(nomEns, idEnseignant, Role.Enseignant);
    }

    public Enseignant(){
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

    public Utilisateur getUtilisateur() { return utilisateur; }

}
