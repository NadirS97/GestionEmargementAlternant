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
    private String email;
    private List<SousModule> sousModules;
    private Utilisateur utilisateur;

    public Enseignant(String nomEns, String prenomEns, String idEnseignant) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.email = prenomEns+"."+nomEns+"@univ-orleans.fr";
        this.sousModules=List.of();
        this.utilisateur = new Utilisateur(this.email, idEnseignant, Role.Enseignant);

    }

    public Enseignant(String nomEns, String prenomEns, String idEnseignant, List<SousModule> sousModules) {
        this.nomEns = nomEns;
        this.prenomEns = prenomEns;
        this.idEnseignant = idEnseignant;
        this.email = prenomEns+"."+nomEns+"@univ-orleans.fr";
        this.sousModules = sousModules;
        this.utilisateur = new Utilisateur(this.email, idEnseignant, Role.Enseignant);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
    public void addSousModule(SousModule sousModule) {
        this.sousModules.add(sousModule);
    }

    public Utilisateur getUtilisateur() { return utilisateur; }

}
