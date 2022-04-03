package fr.orleans.m1.wsi.projets2emargement;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEtudiant;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeGroupe;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Groupe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.swing.*;
import java.util.List;

@SpringBootApplication
public class ProjetS2EmargementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetS2EmargementApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(FacadeEtudiant facadeEtudiant, MongoTemplate mongoTemplate, FacadeGroupe facadeGroup){
        return args -> {
            Etudiant etudiant = new Etudiant(
                    "o1234",
                    "Nom1",
                    "Prenom1",
                    "example@gmail.com"
            );
            facadeEtudiant.insert(etudiant);

            Groupe group = new Groupe(
                    "ALTTP1",
                    List.of(etudiant)
            );
            facadeGroup.insert(group);

        };
    }


}
