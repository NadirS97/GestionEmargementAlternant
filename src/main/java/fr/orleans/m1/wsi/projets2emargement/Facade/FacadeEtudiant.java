package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacadeEtudiant extends MongoRepository<Etudiant,String> {
}
