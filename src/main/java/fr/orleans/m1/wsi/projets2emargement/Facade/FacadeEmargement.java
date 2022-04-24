package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Emargement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacadeEmargement extends MongoRepository<Emargement,String> {
}
