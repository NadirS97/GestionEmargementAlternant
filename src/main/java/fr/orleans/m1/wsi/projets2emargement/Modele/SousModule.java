package fr.orleans.m1.wsi.projets2emargement.Modele;

import java.util.List;

public class SousModule {

    private String nomSM;
    private Module module;
    private TypeCours typeCours;
    private Groupe groupe;

   //ENS

    public SousModule(String nomSM, Module module, TypeCours typeCours, Groupe groupe) {
        this.nomSM = nomSM;
        this.module = module;
        this.typeCours = typeCours;
        this.groupe = groupe;
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


}
