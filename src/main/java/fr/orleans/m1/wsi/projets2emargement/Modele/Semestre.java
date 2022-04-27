package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Semestre {
    @Id
    private String nomS;
    private List<Module> modules;

    public Semestre(String nomS, List<Module> modules) {
        this.nomS = nomS;
        this.modules = modules;
    }

    public Semestre(String nomS) {
        this.nomS = nomS;
        this.modules = List.of();
    }

    public Semestre() {
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    public void addModule(Module module) {
        this.modules.add(module);
    }

    public String getNomS() {
        return nomS;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }
}
