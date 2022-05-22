package fr.orleans.m1.wsi.projets2emargement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeEmargement;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    FacadeEmargement facadeEmargement;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetAllOK() throws Exception {

        List<Emargement> emargements = Arrays.asList(
                new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants()),
                new Emargement(LocalDateTime.parse("2022-05-22T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants())
        );

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findAll()).thenReturn(emargements);

        mockMvc.perform(get("/emargement/")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .content(objectMapper.writeValueAsString(emargements)))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-all-emargements-OK",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testGetAllKO() throws Exception {
        List<Emargement> emargements = new ArrayList<>();

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findAll()).thenReturn(emargements);

        mockMvc.perform(get("/emargement/")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .content(objectMapper.writeValueAsString(emargements)))
                .andExpect(status().isNotFound())
                .andDo(document(
                        "get-all-emargements-KO",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testGetEmargementsOuvertsOK() throws Exception {

        List<Emargement> emargements = Arrays.asList(
                new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants()),
                new Emargement(LocalDateTime.parse("2022-05-22T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants())
        );

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findByEtatEmargement(EtatEmargement.Ouvert)).thenReturn(emargements);

        mockMvc.perform(get("/emargement/ouverts")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .content(objectMapper.writeValueAsString(emargements)))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-emargementsOuverts-OK",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
    @Test
    public void testGetEmargementsClosOK() throws Exception {


         Emargement emargement1 = new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants());
         Emargement emargement2 = new Emargement(LocalDateTime.parse("2022-05-22T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants());
         emargement1.setEtatEmargement(EtatEmargement.Clos);
         emargement2.setEtatEmargement(EtatEmargement.Clos);

         List<Emargement> emargements = new ArrayList<>();
         emargements.add(emargement1);
         emargements.add(emargement2);

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findByEtatEmargement(EtatEmargement.Clos)).thenReturn(emargements);

        mockMvc.perform(get("/emargement/clos")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .content(objectMapper.writeValueAsString(emargements)))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-emargementsClos-OK",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }



//    @Test
//    public void testGetEmargementsOuvertsOK() throws Exception {
//
//    }

}