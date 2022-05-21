package fr.orleans.m1.wsi.projets2emargement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.orleans.m1.wsi.projets2emargement.Modele.Emargement;
import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Salle;
import fr.orleans.m1.wsi.projets2emargement.Modele.SousModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmargementController.class)
@AutoConfigureRestDocs
class EmargementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmargementController emargementController;

    private Emargement emargement;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(
                new Etudiant("1", "Richard", "Jean", Arrays.asList("Groupe4")),
                new Etudiant("2", "TASDEMiR", "Seyma")
                );



        List<Emargement> emargements = Arrays.asList(
                new Emargement("2022-05-10T10:30", , new Salle("EO5"), etudiants )
        );
    }

}