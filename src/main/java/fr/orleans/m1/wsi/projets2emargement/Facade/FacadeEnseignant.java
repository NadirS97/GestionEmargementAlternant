package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Enseignant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacadeEnseignant extends MongoRepository<Enseignant,String> {
}
