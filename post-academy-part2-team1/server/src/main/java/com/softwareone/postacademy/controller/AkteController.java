package com.softwareone.postacademy.controller;

import com.softwareone.postacademy.dto.AkteDTO;
import com.softwareone.postacademy.service.AkteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class AkteController {

    @Autowired
    private AkteService akteService;

    @PostMapping(value = "/akte/all", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
    public ResponseEntity<Object> addAllAkten(@RequestBody List<AkteDTO> listAllAkten){
        for(AkteDTO adto : listAllAkten){
            try{
                AkteDTO akte = akteService.addAkte(adto);
            }
            catch(Exception e){
                e.printStackTrace();
                return  new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500 error
            }
        }
        return  new ResponseEntity<>(HttpStatus.CREATED); //201 content created successfully
    }


    @PostMapping(value="/akte",produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> createAkte(@RequestBody AkteDTO akteDTO){
        try {
            //use service
            AkteDTO akte=akteService.addAkte(akteDTO);
            return  new ResponseEntity<>(akte,
                    HttpStatus.CREATED); //201 content created successfully
        }
        catch(Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500 error
        }
    }

    @GetMapping(value="/akte", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public  ResponseEntity<Object>  fetchAllAkte(){
        try {
            List<AkteDTO> akteDTOList=akteService.getAllAkte();
            return new ResponseEntity<>(akteDTOList,HttpStatus.OK); //200 for sucess
        }
        catch(Exception e) {
            e.printStackTrace();
            return  new  ResponseEntity<>("Problem in fetching ",
                    HttpStatus.INTERNAL_SERVER_ERROR); //500 for failure
        }
    }

    @GetMapping(value="/akte/{akteId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public  ResponseEntity<Object> findAkteById(@PathVariable("akteId") Long akteId){
        try {
            AkteDTO  akteDTO=akteService.fetchAkteById(akteId);
            return new ResponseEntity<>(akteDTO,HttpStatus.OK); //200 for sucess
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.NOT_FOUND); //404 for failure
        }
    }

    @DeleteMapping(value = "/akte/all")
    public void deleteAllAkten(){
        akteService.deleteAllAkten();
    }

    @DeleteMapping(value = "/akte/{akteId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> hardDeleteAkteById(@PathVariable("akteId") Long akteId){
        try {
            AkteDTO akteDTO = akteService.hardDeleteAkteById(akteId);
            return new ResponseEntity<>(akteDTO, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="/letzteheftnummer", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public  ResponseEntity<Object> findLastHeftNUmber(){
        try {
            Long lastHeftNumber=akteService.findLastHeftNumber();
            return new ResponseEntity<>(lastHeftNumber,HttpStatus.OK); //200 for success
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR); //500 for failure
        }
    }

    @PutMapping(value="/akte",produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public   ResponseEntity<Object>  modifyAkte(@RequestBody AkteDTO akteDTO){
        try {
            AkteDTO akte= akteService.updateAkte(akteDTO);
            return new ResponseEntity<>(akte,HttpStatus.OK);
        }

        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST); //400
        }
    }

    @GetMapping(value="/papierkorb", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public  ResponseEntity<?>  fetchAllAkteFromPapierkorb(){
        try {
            List<AkteDTO> akteDTOList=akteService.getAllAktenFromPapierkorb();
            return new ResponseEntity<>(akteDTOList,HttpStatus.OK); //200 for sucess
        }
        catch(Exception e) {
            e.printStackTrace();
            return  new  ResponseEntity<>("Problem in fetching ",
                    HttpStatus.INTERNAL_SERVER_ERROR); //500 for failure
        }
    }

    @PatchMapping("/akte/{akteId}/{papierkorb}")
    public   ResponseEntity<Object>  temporaryDeletionAndRestoreById(@PathVariable("akteId") Long akteId,
                                                             @PathVariable("papierkorb") boolean  papierKorb){
        try {
            //use service
            AkteDTO akteDTO=akteService.temporaryDeletionAndRestore(akteId,papierKorb);
            return new ResponseEntity<>(akteDTO,HttpStatus.OK);
        }//try
        catch(Exception e) {
            e.printStackTrace();
            return new  ResponseEntity<>(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping(value = "/akteMultiple", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> delete(@RequestParam("akteIdList") List<Long> akteIdList) {

        try {
            String msg = akteService.deleteMultipleAktenPermanently(akteIdList);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/akte/filterByFields")
    public  ResponseEntity<?>  findAkteByHeftnummer(@RequestParam(value = "heftnummer",required = false) Long heftnummer,@RequestParam(value = "flurstueck",required = false) String flurStueck){
        try {
            //use service
            System.out.println("rakekkkkkkkkkkk");
            System.out.println(heftnummer);
            System.out.println(flurStueck);
            List<AkteDTO> akteDTOList =akteService.findAktenByFiltering(heftnummer,flurStueck);
            return  new ResponseEntity<>(akteDTOList,HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<String>(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

    }//method




}