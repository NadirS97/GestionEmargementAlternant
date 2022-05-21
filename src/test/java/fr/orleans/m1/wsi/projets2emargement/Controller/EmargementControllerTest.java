package fr.orleans.m1.wsi.projets2emargement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class EmargementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DataTest dataTest ;

    @MockBean
    EmargementController emargementController;

    @MockBean
    UtilisateurController utilisateurController;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetAllOK() throws Exception {

        List<Emargement> emargements = Arrays.asList(
                new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants())
        );

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Utilisateur utilisateur = new Utilisateur(loginAdmin, passwordAdmin, Role.PersonnelAdm);

        utilisateurController.creerUtilisateur(utilisateur);

        when(emargementController.getAll()).thenReturn(ResponseEntity.ok().body(emargements));

        mockMvc.perform(get("/emargement/")
                        .with(httpBasic(loginAdmin,passwordAdmin)))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-all-emargements",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }


//    @Test
//    public void testGetAllKO() throws Exception {
//        List<Emargement> emargements = Arrays.asList(
//                new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants())
//        );
//        String loginAdmin = dataTest.getLoginAdmin();
//        String passwordAdmin = dataTest.getPasswordAdmin();
//
//        Utilisateur utilisateur = new Utilisateur(loginAdmin, passwordAdmin, Role.Etudiant);
//
//        when(emargementController.getAll()).thenReturn(ResponseEntity.ok().body(emargements));
//        mockMvc.perform(get("/emargement/")
//                        .with(httpBasic(loginAdmin,passwordAdmin)))
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "get-all-emargements-NonAutorisé",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                ));
//    }


}