package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Module {
    @Id
    private String code;
    private String nomM;
    private Semestre semestre;
    private List<SousModule> sousModules;

    public Module(String code, String nomM, Semestre semestre) {
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

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
}
