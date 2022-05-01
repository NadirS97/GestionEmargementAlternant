package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;

import java.util.List;

public class SousModule {
    @Id
    private String nomSM;
    private String module;
    private TypeCours typeCours;
    private String groupe;
    private String enseignant;

   //ENS

    public SousModule() {
    }

    public SousModule(String nomSM, String module, TypeCours typeCours, String groupe , String enseignant) {
        this.nomSM = nomSM;
        this.module = module;
        this.typeCours = typeCours;
        this.groupe = groupe;
        this.enseignant=enseignant;
    }

    public String getNomSM() {
        return nomSM;
    }

    public void setNomSM(String nomSM) {
        this.nomSM = nomSM;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }
}
