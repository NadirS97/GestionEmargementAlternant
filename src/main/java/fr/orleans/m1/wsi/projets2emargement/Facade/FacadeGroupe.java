package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Groupe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacadeGroupe extends MongoRepository<Groupe,String> {
}
