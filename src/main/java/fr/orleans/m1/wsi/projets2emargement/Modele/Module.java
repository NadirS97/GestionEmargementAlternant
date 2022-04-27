package fr.orleans.m1.wsi.projets2emargement.Modele;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeSemestre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Module {
    @Id
    private String code;
    private String nomM;
    private String semestre;
    private List<SousModule> sousModules;


    public Module(String code, String nomM, String semestre) {
        this.code = code;
        this.nomM = nomM;
        this.semestre = semestre;
        this.sousModules = List.of();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomM() {
        return nomM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
