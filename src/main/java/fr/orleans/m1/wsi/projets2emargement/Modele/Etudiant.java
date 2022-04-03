package fr.orleans.m1.wsi.projets2emargement.Modele;

import java.util.List;

public class Etudiant {

    private String numEtu;
    private String nom;
    private String prenom;
    private String email;
    private Etat etat;
    private List<Groupe> groupes;
    private final Utilisateur utilisateur;

    public Etudiant(String numEtu, String nom, String prenom, String email, List<Groupe> groupes) {
        this.numEtu = numEtu;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etat = Etat.ABSENT;
        this.groupes = groupes;
        this.utilisateur = new Utilisateur(email, numEtu, Role.Etudiant);
    }
    public Etudiant(String numEtu, String nom, String prenom, String email) {
        this.numEtu = numEtu;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etat = Etat.ABSENT;
        this.groupes = List.of();
        this.utilisateur = new Utilisateur(email, numEtu, Role.Etudiant);
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

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public void addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
    }

    public Utilisateur getUtilisateur() { return utilisateur; }

}
