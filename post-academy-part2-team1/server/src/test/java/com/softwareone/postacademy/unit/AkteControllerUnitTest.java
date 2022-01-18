package com.softwareone.postacademy.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareone.postacademy.controller.AkteController;
import com.softwareone.postacademy.dto.AkteDTO;
import com.softwareone.postacademy.model.GrundstuecksInformation;
import com.softwareone.postacademy.service.AkteService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AkteController.class)
public class AkteControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jsonSerializer;

    //mocking akteService class as this method is dependent on it
    @MockBean
    private AkteService akteService;

    @Test
    public void creatingNewAkteTest() throws Exception{

        // mock the akte data that we need to add
        AkteDTO akte= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);

        // mock data of GrundstuecksInformation for the akte which is being added
        GrundstuecksInformation GrundstuecksInformation = new GrundstuecksInformation(1L,20L,34L,"34/52"
                , new Date(2021-5-9),4L, "345/952", "The akten 1st contract");

        // Creating list of grundstuecksInformationen
        List<GrundstuecksInformation> grundstuecksInformationenList = List.of(GrundstuecksInformation);

        // setting the grundstuecksInformationen to the akte that is being created
        akte.setAllGrundstuecksInformationen(grundstuecksInformationenList);

        // setting the behavior by stubbing, so any of the AKteDTO class is passed as the argument it retuns the akte
        Mockito.when(akteService.addAkte(Mockito.any(AkteDTO.class))).thenReturn(akte);

        // Using object mapper to convert java object to json
        String akteToBeAddedJson = jsonSerializer.writeValueAsString(akte);

        //sending the http request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/akte").contentType(MediaType.APPLICATION_JSON).content(akteToBeAddedJson);

        // verifying the obtained data
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(11L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.letzteHeftnummer").value(0L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neueHeftnummer").value(1L))
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
    public void findAkteByIdTest() throws Exception{

        // mock the akte data that will be returned by service class
        AkteDTO expectedAkte= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);

        // mock data of GrundstuecksInformation for the this particular akte which we get by id
        GrundstuecksInformation GrundstuecksInformation = new GrundstuecksInformation(1L,20L,34L,"34/52"
                , new Date(2021-5-9),4L, "345/952", "The akten 1st contract");

        // Creating list of grundstuecksInformationen
        List<GrundstuecksInformation> grundstuecksInformationenList = List.of(GrundstuecksInformation);

        // setting the grundstuecksInformationen to the akte that is being fetched
        expectedAkte.setAllGrundstuecksInformationen(grundstuecksInformationenList);

        Mockito.when(akteService.fetchAkteById(Mockito.anyLong())).thenReturn(expectedAkte);

        //sending the http request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/akte/1");

        // verifying the result
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(11L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.letzteHeftnummer").value(0L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neueHeftnummer").value(1L))
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
    public void getAllAkteTest() throws Exception{

        // Considering that fetchAllAkte from service method returns 2 records i.e., akte1 and akte2
        AkteDTO akte1= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs",false, null,null);

        AkteDTO akte2= new AkteDTO(2L,3L,12L,new Date(2020-1-7),
                1L,2L,false,"second akte", "other docs", false, null,null);

        // List of akten that are returned form service class
        List<AkteDTO> expectedAkten = List.of(akte1, akte2);

        // defining the behaciour through stubbing
        Mockito.when(akteService.getAllAkte()).thenReturn(expectedAkten);

        // verifying the obtained values
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/akte")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].stadtBezirk").value(2L))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[1].stadtBezirk").value(3L));
    }

    @Test
    public void findLastHeftNumberTest() throws Exception {

        //considering the expected heftnumber value to be 2L
        Long expectedHeftNumber=2L;

        // defining the behaviour through stubbing
        Mockito.when(akteService.findLastHeftNumber()).thenReturn(expectedHeftNumber);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/letzteheftnummer")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$").value(2L));
    }

    @Test
    public void updateAkteAddGrundstuecksInformationTest() throws Exception {
        AkteDTO akte1= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);
        // mock data of GrundstuecksInformation for the this particular akte which we get by id
        GrundstuecksInformation GrundstuecksInformation = new GrundstuecksInformation(1L,20L,34L,"34/52"
                , new Date(2021-5-9),4L, "345/952", "The akten 1st contract");

        // Creating list of grundstuecksInformationen
        List<GrundstuecksInformation> grundstuecksInformationenList = List.of(GrundstuecksInformation);

        // setting the grundstuecksInformationen to the akte that is being fetched
        akte1.setAllGrundstuecksInformationen(grundstuecksInformationenList);
        String akteToBeChangedJson = jsonSerializer.writeValueAsString(akte1);

        //mock service response
        Mockito.when(akteService.updateAkte(Mockito.any(AkteDTO.class))).thenReturn(akte1);
        //sending the http request
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/akte")
                .contentType(MediaType.APPLICATION_JSON).content(akteToBeChangedJson);

        // verifying the result
        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(11L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.letzteHeftnummer").value(0L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neueHeftnummer").value(1L))
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
    public void updateAkteChangeKennZifferTest() throws Exception {
        AkteDTO akte1= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);
        String akteToBeChangedJson = jsonSerializer.writeValueAsString(akte1);

        //mock service response
        Mockito.when(akteService.updateAkte(Mockito.any(AkteDTO.class))).thenReturn(akte1);
        //sending the http request
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/akte")
                .contentType(MediaType.APPLICATION_JSON).content(akteToBeChangedJson);

        // verifying the result
        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stadtBezirk").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kennZiffer").value(11L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.letzteHeftnummer").value(0L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neueHeftnummer").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.almosenKasten").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.betreff").value("first akte"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sonstigeAnmerkungen").value("other docs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allGrundstuecksInformationen").isEmpty());
    }

    @Test
    public void changeAkteThrowsErrorWithWrongId() throws Exception {
        AkteDTO akte1= new AkteDTO(1L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);
        String akteToBeChangedJson = jsonSerializer.writeValueAsString(akte1);
        //mock service response
        Mockito.when(akteService.updateAkte(Mockito.any(AkteDTO.class))).thenThrow(new Exception());
        //sending the http request
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/akte")
                .contentType(MediaType.APPLICATION_JSON).content(akteToBeChangedJson);

        mockMvc.perform(putRequest).andExpect(status().is4xxClientError());
    }


    @Test
    public void deleteAkteById_success() throws Exception {
        AkteDTO akte1= new AkteDTO(5L,2L,11L,new Date(2020-1-7),
                0L,1L,false,"first akte", "other docs", false, null,null);

        Mockito.when(akteService.hardDeleteAkteById(akte1.getAkteId())).thenReturn(akte1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/akte/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.akteId").value(5L));
    }


//    @Test
//    public void deleteAkteById_notFound() throws Exception {
//        Mockito.when(akteService.hardDeleteAkteById(500L)).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/akte/500")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }



}