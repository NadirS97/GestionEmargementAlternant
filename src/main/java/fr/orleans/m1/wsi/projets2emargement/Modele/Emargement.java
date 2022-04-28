package fr.orleans.m1.wsi.projets2emargement.Modele;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeGroupe;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class Emargement {

    private String idEmargement;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private SousModule sousModule;
    private List<Etudiant> etudiantsAbsents;
    private List<Etudiant> etudiantsPresents;
    private EtatEmargement etatEmargement;
    @Autowired
    FacadeGroupe facadeGroupe;

    // TODO QR CODE


    public Emargement(String idEmargement,LocalDateTime heureDebut, LocalDateTime heureFin, SousModule sousModule) {
        this.idEmargement = idEmargement;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.sousModule = sousModule;
        this.etudiantsAbsents = facadeGroupe.findById(sousModule.getGroupe()).get().getEtudiants();
        this.etudiantsPresents = List.of();
        this.etatEmargement = EtatEmargement.Ouvert;
    }

    public Emargement(String idEmargement, LocalDateTime heureFin, SousModule sousModule) {
        this.idEmargement = idEmargement;
        this.heureDebut = LocalDateTime.now();
        this.heureFin = heureFin;
        this.sousModule = sousModule;
        this.etudiantsAbsents = facadeGroupe.findById(sousModule.getGroupe()).get().getEtudiants();
        this.etudiantsPresents = List.of();
        this.etatEmargement = EtatEmargement.Ouvert;
    }

    public String getIdEmargement() {
        return idEmargement;
    }

    public void setIdEmargement(String idEmargement) {
        this.idEmargement = idEmargement;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalDateTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalDateTime heureFin) {
        this.heureFin = heureFin;
    }

    public SousModule getSousModule() {
        return sousModule;
    }

    public void setSousModule(SousModule sousModule) {
        this.sousModule = sousModule;
    }

    public List<Etudiant> getEtudiantsAbsents() {
        return etudiantsAbsents;
    }

    public void setEtudiantsAbsents(List<Etudiant> etudiantsAbsents) {
        this.etudiantsAbsents = etudiantsAbsents;
    }

    public List<Etudiant> getEtudiantsPresents() {
        return etudiantsPresents;
    }

    public void setEtudiantsPresents(List<Etudiant> etudiantsPresents) {
        this.etudiantsPresents = etudiantsPresents;
    }

    public EtatEmargement getEtatEmargement() {
        return etatEmargement;
    }

    public void setEtatEmargement(EtatEmargement etatEmargement) {
        this.etatEmargement = etatEmargement;
    }
}
