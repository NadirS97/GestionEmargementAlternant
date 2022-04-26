package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.OptionalDouble;

public interface FacadeEtudiant extends MongoRepository<Etudiant,String> {
    Optional<Etudiant> findEtudiantByNumEtu(String numEtu);
    Optional<Etudiant> findEtudiantByEmail(String email);
    void deleteAllByNumEtu(String NumEtu);

}
