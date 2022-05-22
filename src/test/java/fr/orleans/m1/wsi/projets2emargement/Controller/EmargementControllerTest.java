package fr.orleans.m1.wsi.projets2emargement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.orleans.m1.wsi.projets2emargement.Facade.*;
import fr.orleans.m1.wsi.projets2emargement.Modele.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    FacadeSalle facadeSalle;

    @MockBean
    FacadeModule facadeModule;

    @MockBean
    FacadeSousModule facadeSousModule;

    @MockBean
    FacadeGroupe facadeGroupe;

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

        Emargement emargement1 = new Emargement(LocalDateTime.parse("2022-05-10T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants());
        Emargement emargement2 = new Emargement(LocalDateTime.parse("2022-05-22T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants());
        emargement1.setEtatEmargement(EtatEmargement.Clos);
        emargement2.setEtatEmargement(EtatEmargement.Ouvert);


        List<Emargement> emargements = new ArrayList<>();
        emargements.add(emargement1);
        emargements.add(emargement2);

        List<Emargement> emargementOuvert = new ArrayList<>();
        emargementOuvert.add(emargement2);

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findByEtatEmargement(EtatEmargement.Ouvert)).thenReturn(emargementOuvert);

        mockMvc.perform(MockMvcRequestBuilders.get("/emargement/ouverts")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
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
         emargement2.setEtatEmargement(EtatEmargement.Ouvert);


         List<Emargement> emargements = new ArrayList<>();
         emargements.add(emargement1);
         emargements.add(emargement2);

        List<Emargement> emargementClos = new ArrayList<>();
        emargementClos.add(emargement1);

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Mockito.when(facadeEmargement.findByEtatEmargement(EtatEmargement.Clos)).thenReturn(emargementClos);

        mockMvc.perform(MockMvcRequestBuilders.get("/emargement/clos")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document(
                        "get-emargementsClos-OK",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testCreerEmargementOK() throws Exception {

        String loginAdmin = dataTest.getLoginAdmin();
        String passwordAdmin = dataTest.getPasswordAdmin();

        Emargement emargement = new Emargement(LocalDateTime.parse("2022-05-28T10:30"), dataTest.getSousModule(), dataTest.getSalle(), dataTest.getEtudiants());

        Mockito.when(facadeEmargement.save(emargement)).thenReturn(emargement);
        Mockito.when(facadeSalle.findById(dataTest.getSalle().getNomSalle())).thenReturn(Optional.of(dataTest.getSalle()));
        Mockito.when(facadeSousModule.findById(dataTest.getSousModule().getNomSM())).thenReturn(Optional.of(dataTest.getSousModule()));
        Mockito.when(facadeGroupe.findById(dataTest.getSousModule().getGroupe())).thenReturn(Optional.of(dataTest.getGroupe()));

        mockMvc.perform(post("/emargement/")
                        .with(httpBasic(loginAdmin,passwordAdmin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emargement)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(header().exists("Location"))
                .andDo(document(
                        "post-creationEmargement-OK",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}