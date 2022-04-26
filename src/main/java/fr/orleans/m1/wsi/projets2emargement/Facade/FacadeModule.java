package fr.orleans.m1.wsi.projets2emargement.Facade;
import org.springframework.data.mongodb.repository.MongoRepository;
import fr.orleans.m1.wsi.projets2emargement.Modele.Module;

public interface FacadeModule extends MongoRepository<Module,String> {

}
