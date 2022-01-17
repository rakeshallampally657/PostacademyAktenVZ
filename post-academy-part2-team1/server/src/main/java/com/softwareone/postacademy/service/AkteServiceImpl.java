package com.softwareone.postacademy.service;

import com.softwareone.postacademy.dto.AkteDTO;
import com.softwareone.postacademy.model.Akte;
import com.softwareone.postacademy.repository.AkteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AkteServiceImpl implements AkteService {

    @Autowired
    private AkteRepository akteRepository;

    /**
    converting DTO to Model
     */
    private Akte convertDTOToModel(AkteDTO akteDTO){
        Akte akte = new Akte();
        akte.setAkteId(akteDTO.getAkteId());
        akte.setAllAenderungen(akteDTO.getAllAenderungen());
        akte.setAlmosenKasten(akteDTO.isAlmosenKasten());
        akte.setStadtBezirk(akteDTO.getStadtBezirk());
        akte.setKennZiffer(akteDTO.getKennZiffer());
        akte.setAktenBeginn(akteDTO.getAktenBeginn());
        akte.setLetzteHeftnummer(akteDTO.getLetzteHeftnummer());
        akte.setNeueHeftnummer(akteDTO.getNeueHeftnummer());
        akte.setInPapierKorb(akteDTO.isInPapierKorb());
        akte.setBetreff(akteDTO.getBetreff());
        akte.setSonstigeAnmerkungen(akteDTO.getSonstigeAnmerkungen());
        akte.setAllGrundstuecksInformationen(akteDTO.getAllGrundstuecksInformationen());
        return akte;
    }

    /**
    *converting model to DTO
    */
    private AkteDTO convertModelToDTO(Akte akte){
        return new AkteDTO(akte);
    }

    @Override
    public AkteDTO addAkte(AkteDTO akteDTO) {
        Akte akte= convertDTOToModel(akteDTO);
        Long letzteHeftnummer= akteRepository.findLastHeftnummer();
        if(letzteHeftnummer== null){
            letzteHeftnummer=0L;
        }
        akte.setLetzteHeftnummer(letzteHeftnummer);
        akte.setNeueHeftnummer(letzteHeftnummer+1);
        return convertModelToDTO(akteRepository.save(akte));
    }

    @Override
    public List<AkteDTO> getAllAkte() {
         List<Akte> akteList= akteRepository.findAll();
         List<AkteDTO> akteDTOList= new ArrayList<>();
         for(Akte akte: akteList){
             akteDTOList.add(convertModelToDTO(akte));
         }
         return akteDTOList;
    }

    @Override
    public AkteDTO fetchAkteById(Long akteId) throws Exception {
        Akte akte= akteRepository.findById(akteId)
                .orElseThrow(() -> new Exception("AKTE ID NOT FOUND EXCEPTION :::"+akteId));
        return convertModelToDTO(akte);
    }

    @Override
    public Long findLastHeftNumber() {
        Long letzteHeftnummer= akteRepository.findLastHeftnummer();
        if(letzteHeftnummer== null){
            letzteHeftnummer=0L;
        }
        return letzteHeftnummer;
    }

    @Override
    public AkteDTO updateAkte (AkteDTO akteDTO) throws Exception{
        Optional<Akte> akteObtained = akteRepository.findById(akteDTO.getAkteId());
        if(akteObtained.isPresent()){
            Akte akte=akteRepository.save(convertDTOToModel(akteDTO));
            return convertModelToDTO(akte);
        }
      else {
          throw new Exception("AKTE ID TO BE UPDATED NOT FOUND EXCEPTION :::"+ akteDTO.getAkteId());
        }
    }

    @Override
    public AkteDTO hardDeleteAkteById(Long akteId) throws  Exception{
        Akte akte = akteRepository.findById(akteId).orElseThrow(() -> new Exception("AKTE ID NOT FOUND EXCEPTION :::"+akteId));
        AkteDTO akteDTO = convertModelToDTO(akte);
        akteRepository.delete(akte);
        return akteDTO;
    }
}