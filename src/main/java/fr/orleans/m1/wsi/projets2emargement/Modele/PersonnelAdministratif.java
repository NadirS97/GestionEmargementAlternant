package fr.orleans.m1.wsi.projets2emargement.Modele;

public class PersonnelAdministratif {

    private String idAdm;
    private String nomAdm;
    private String prenomAdm;
    private final Utilisateur utilisateur;

    public PersonnelAdministratif(String idAdm, String nomAdm, String prenomAdm) {
        this.idAdm = idAdm;
        this.nomAdm = nomAdm;
        this.prenomAdm = prenomAdm;
        this.utilisateur = new Utilisateur(nomAdm, idAdm, Role.PersonnelAdm);
    }

    public String getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(String idAdm) {
        this.idAdm = idAdm;
    }

    public String getNomAdm() {
        return nomAdm;
    }

    public void setNomAdm(String nomAdm) {
        this.nomAdm = nomAdm;
    }

    public String getPrenomAdm() {
        return prenomAdm;
    }

    public void setPrenomAdm(String prenomAdm) {
        this.prenomAdm = prenomAdm;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

}
