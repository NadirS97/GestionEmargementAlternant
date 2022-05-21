package fr.orleans.m1.wsi.projets2emargement;

import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ProjetS2EmargementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjetS2EmargementApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(FacadeUtilisateur fuser, MongoTemplate mongoTemplate) {
        return args -> {
            Utilisateur admin = new Utilisateur(
                    "Admin", "Admin", Role.PersonnelAdm
            );
            fuser.findUtilisateurByLogin(admin.getLogin())
                    .ifPresentOrElse(s -> {
                        System.out.println("Admin already exists.");
                    }, () -> {
                        fuser.insert(admin);
                    });

        };
    }


}
