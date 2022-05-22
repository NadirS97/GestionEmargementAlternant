package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Emargement;
import fr.orleans.m1.wsi.projets2emargement.Modele.EtatEmargement;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FacadeEmargement extends MongoRepository<Emargement,String> {
    Optional<Emargement> findByHeureDebutAndHeureFinAndSalle(LocalDateTime hd, LocalDateTime hf, Salle salle);
    //List<Optional<Emargement>> findByEtatEmargement(String etatEmargement);
    List<Emargement> findByEtatEmargement(EtatEmargement etatEmargement);
    List<Optional<Emargement>> findEmargementsByHeureDebutBeforeAndHeureFinAfter(LocalDateTime hd, LocalDateTime hf);
}
