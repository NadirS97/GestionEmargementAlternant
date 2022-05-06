package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;

public class Salle {
    @Id
    String nomSalle;

    public Salle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Salle() {
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }
}
