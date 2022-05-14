package fr.orleans.m1.wsi.projets2emargement.Modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
public class Emargement {
    @Id
    private String idEmargement;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private SousModule sousModule;
    private List<Etudiant> etudiantsAbsents;
    private List<Etudiant> etudiantsPresents;
    private EtatEmargement etatEmargement;
    private  Salle salle;


    public Emargement(){}

    public Emargement(LocalDateTime heureDebut, LocalDateTime heureFin, SousModule sousModule,Salle salle,List<Etudiant> etudiantsAbsents) {
        this.idEmargement= UUID.randomUUID().toString();
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.sousModule = sousModule;
        this.salle=salle;
        this.etudiantsAbsents = etudiantsAbsents;
        this.etudiantsPresents = List.of();
        this.etatEmargement = EtatEmargement.Ouvert;

    }

    public Emargement( LocalDateTime heureFin, SousModule sousModule,Salle salle,List<Etudiant> etudiantsAbsents) {
        this.idEmargement= UUID.randomUUID().toString();
        this.heureDebut = LocalDateTime.now();
        this.heureFin = heureFin;
        this.sousModule = sousModule;
        this.salle=salle;
        this.etudiantsAbsents = etudiantsAbsents;
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
    public void addEtudiantsPresents(Etudiant etudiantPresent) {
        this.etudiantsPresents.add(etudiantPresent);
    }

    public EtatEmargement getEtatEmargement() {
        return etatEmargement;
    }

    public void setEtatEmargement(EtatEmargement etatEmargement) {
        this.etatEmargement = etatEmargement;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
