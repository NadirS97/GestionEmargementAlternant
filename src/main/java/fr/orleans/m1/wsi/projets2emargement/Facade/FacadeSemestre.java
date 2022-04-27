package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Semestre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacadeSemestre extends MongoRepository<Semestre,String> {

}
