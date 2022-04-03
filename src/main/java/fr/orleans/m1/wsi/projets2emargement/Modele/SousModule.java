package fr.orleans.m1.wsi.projets2emargement.Modele;

import java.util.List;

public class SousModule {

    private String nomSM;
    private Module module;
    private TypeCours typeCours;
    private Groupe groupe;
    private Enseignant enseignant;

   //ENS

    public SousModule(String nomSM, Module module, TypeCours typeCours, Groupe groupe ,Enseignant enseignant) {
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
}
