package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacadeSalle extends MongoRepository<Salle,String> {
}
