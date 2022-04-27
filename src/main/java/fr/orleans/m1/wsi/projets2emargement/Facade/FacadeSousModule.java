package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Enseignant;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FacadeSousModule  extends MongoRepository<SousModule,String> {
    Optional<SousModule>[] findAllByEnseignant(Enseignant enseignant);
}
