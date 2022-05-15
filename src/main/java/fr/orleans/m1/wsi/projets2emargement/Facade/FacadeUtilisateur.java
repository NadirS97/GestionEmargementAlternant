package fr.orleans.m1.wsi.projets2emargement.Facade;

import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacadeUtilisateur extends MongoRepository<Utilisateur, String> {
    Optional<Utilisateur> findUtilisateurByLogin(String login);
}
