package fr.orleans.m1.wsi.projets2emargement.Facade;
import org.springframework.data.mongodb.repository.MongoRepository;
import fr.orleans.m1.wsi.projets2emargement.Modele.Module;

import java.util.Optional;

public interface FacadeModule extends MongoRepository<Module,String> {
    Optional<Module>[] findBySemestre(String semestre);
}
