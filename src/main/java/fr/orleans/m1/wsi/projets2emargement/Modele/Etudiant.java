package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Etudiant {

    @Id
    private String numEtu;
    private String nom;
    private String prenom;
    private String email;
    private Etat etat;
    private List<String> groupes;
    private Utilisateur utilisateur;

    public Etudiant(String numEtu, String nom, String prenom, List<String> groupes) {
        this.numEtu = numEtu;
        this.nom = nom;
        this.prenom = prenom;
        this.email = prenom+"."+nom+"@etu.univ-orleans.fr";
        this.etat = Etat.ABSENT;
        this.groupes = groupes;
        this.utilisateur = new Utilisateur(this.email, numEtu, Role.Etudiant);
    }
    public Etudiant(String numEtu, String nom, String prenom) {
        this.numEtu = numEtu;
        this.nom = nom;
        this.prenom = prenom;
        this.email = prenom+"."+nom+"@etu.univ-orleans.fr";
        this.etat = Etat.ABSENT;
        this.groupes = List.of();
        this.utilisateur = new Utilisateur(this.email, numEtu, Role.Etudiant);
    }
    public Etudiant() {
    }

    public String getNumEtu() {
        return numEtu;
    }

    public void setNumEtu(String numEtu) {
        this.numEtu = numEtu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<String> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<String> groupes) {
        this.groupes = groupes;
    }

    public void addGroupe(String groupe) {
        this.groupes.add(groupe);
    }

    public Utilisateur getUtilisateur() { return utilisateur; }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
