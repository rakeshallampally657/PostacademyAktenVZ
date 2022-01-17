package com.softwareone.postacademy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareone.postacademy.PostacademyApplication;
import com.softwareone.postacademy.dto.AkteDTO;
import com.softwareone.postacademy.model.GrundstuecksInformation;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = PostacademyApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class AkteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // for sending the http request

    /* The test.sql is the file that contains two akten information i.e., akteId=1 and akteId=2,
    which is loaded in the in-memory database using h2 as the datasource*/
    @Sql({"/test.sql"})
    @Test
    public void allAktenFetchingTest() throws Exception
    {
        // The akten information is loaded in the in-memory database using test.sql script
        //Getting all the akten information and verfying is the akteId is present
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/akte")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists()) //checking whether akte exsist
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].akteId").isNotEmpty()); // Ensuring akteId is not empty
    }

    @Test
    public void getAkteByIdTest() throws Exception{
        // The akten information is loaded in the in-memory database using test.sql script
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/akte/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(2)); //Verifying whether the obtained akteId=2
    }

    @Test
    public void createNewAkteTest() throws Exception {
        //Created the akte data object which will be passed as the request body for post http request
        AkteDTO akte= new AkteDTO(3L,3L,13L,new Date(2020-1-7),
                2L,3L,false,"third akte", "other docs", false, null,null);

        ObjectMapper jsonSerializer = new ObjectMapper();
        //converting java object into json
        String akteToBeCreatedJson = jsonSerializer.writeValueAsString(akte);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/akte")
                        .content(akteToBeCreatedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(13L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.almosenKasten").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.betreff").value("third akte"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sonstigeAnmerkungen").value("other docs"));
    }

    @Test
    public void findLastHeftNumberTest() throws Exception{
        // The akten information is loaded in the in-memory database using test.sql script
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/letzteheftnummer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Verifying whether the obtained LastheftNumberValue=3 because lastHeftNumber in this inMemory database is 3
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    public void updateAkteAndGrundstuecksInformationTest() throws Exception {

        //The akte that has to be updated , as akte with akteId=3 is already present in in-memory h2 database, so modifying its data
        AkteDTO akte= new AkteDTO(1L,30L,33L,new Date(2020-1-7),
                10L,11L,false,"first akte", "other docs",false, null,null);

        //Adding GrundstuecksInformation to akte
        GrundstuecksInformation grundstuecksInformation = new GrundstuecksInformation(1L,20L,34L,"34/52"
                , new Date(2021-5-9),4L, "345/952", "The akten 1st contract");

        // Creating list of grundstuecksInformationen
        List<GrundstuecksInformation> grundstuecksInformationenList = List.of(grundstuecksInformation);

        // setting the grundstuecksInformationen to the akte that is being fetched
        akte.setAllGrundstuecksInformationen(grundstuecksInformationenList);

        ObjectMapper jsonSerializer = new ObjectMapper();
        //converting java object into json
        String akteToBeUpdatedJson = jsonSerializer.writeValueAsString(akte);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/akte")
                        .content(akteToBeUpdatedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(30L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(33L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.letzteHeftnummer").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neueHeftnummer").value(11L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.almosenKasten").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.betreff").value("first akte"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sonstigeAnmerkungen").value("other docs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].gemarkung").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].flur").value(34L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].flurStueck").value("34/52"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].laufzeit").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].vertragsNummer").value("345/952"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen[0].anmerkung").value("The akten 1st contract"));
    }

    @Test
    public void updateNonExistingAkte() throws Exception {

        //The akte with akteId=10L is not present in h2 database, when trying to update it , it should throw an error
        AkteDTO akte= new AkteDTO(10L,25L,32L,new Date(2020-1-7),
                10L,11L,false,"first akte", "other docs",false, null,null);

        ObjectMapper jsonSerializer = new ObjectMapper();
        //converting java object into json
        String akteToBeUpdatedJson = jsonSerializer.writeValueAsString(akte);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/akte")
                        .content(akteToBeUpdatedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())// it throws error when trying to update non-existing akte
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").doesNotExist());
    }
}