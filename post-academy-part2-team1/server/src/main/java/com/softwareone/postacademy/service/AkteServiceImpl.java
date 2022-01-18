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
     * converting DTO to Model
     */
    private Akte convertDTOToModel(AkteDTO akteDTO) {
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
     * converting model to DTO
     */
    private AkteDTO convertModelToDTO(Akte akte) {
        return new AkteDTO(akte);
    }

    @Override
    public AkteDTO addAkte(AkteDTO akteDTO) {
        Akte akte = convertDTOToModel(akteDTO);
        Long letzteHeftnummer = akteRepository.findLastHeftnummer();
        if (letzteHeftnummer == null) {
            letzteHeftnummer = 0L;
        }
        akte.setLetzteHeftnummer(letzteHeftnummer);
        akte.setNeueHeftnummer(letzteHeftnummer + 1);
        return convertModelToDTO(akteRepository.save(akte));
    }

    @Override
    public List<AkteDTO> getAllAkte() {
        List<Akte> akteList = akteRepository.findAllAkten();
        List<AkteDTO> akteDTOList = new ArrayList<>();
        for (Akte akte : akteList) {
            akteDTOList.add(convertModelToDTO(akte));
        }
        return akteDTOList;
    }

    @Override
    public AkteDTO fetchAkteById(Long akteId) throws Exception {
        Akte akte = akteRepository.findById(akteId)
                .orElseThrow(() -> new Exception("AKTE ID NOT FOUND EXCEPTION :::" + akteId));
        return convertModelToDTO(akte);
    }

    @Override
    public Long findLastHeftNumber() {
        Long letzteHeftnummer = akteRepository.findLastHeftnummer();
        if (letzteHeftnummer == null) {
            letzteHeftnummer = 0L;
        }
        return letzteHeftnummer;
    }

    @Override
    public AkteDTO updateAkte(AkteDTO akteDTO) throws Exception {
        Optional<Akte> akteObtained = akteRepository.findById(akteDTO.getAkteId());
        if (akteObtained.isPresent()) {
            Akte akte = akteRepository.save(convertDTOToModel(akteDTO));
            return convertModelToDTO(akte);
        } else {
            throw new Exception("AKTE ID TO BE UPDATED NOT FOUND EXCEPTION :::" + akteDTO.getAkteId());
        }
    }

    @Override
    public AkteDTO hardDeleteAkteById(Long akteId) throws Exception {

        Optional<Akte> akteObtained = akteRepository.findById(akteId);
        if (akteObtained.isPresent()) {
            akteRepository.delete(akteObtained.get());
            return convertModelToDTO(akteObtained.get());
        } else {
            throw new Exception("AKTE ID TO BE DELETED NOT FOUND EXCEPTION :::" + akteId);
        }

    }


    @Override
    public void deleteAllAkten() {

        List<Akte> allAkten = akteRepository.findAll();
        while (!allAkten.isEmpty()) {
            akteRepository.delete(allAkten.get(0));
            allAkten.remove(0);
        }
    }

    @Override
    public List<AkteDTO> getAllAktenFromPapierkorb() {
        List<Akte> akteList = akteRepository.findAllAktenFromPapierKorb();
        List<AkteDTO> akteDTOList = new ArrayList<>();
        for (Akte akte : akteList) {
            akteDTOList.add(convertModelToDTO(akte));
        }
        return akteDTOList;
    }

    @Override
    public AkteDTO temporaryDeletionAndRestore(Long akteId, boolean papierKorb) throws Exception {
        Optional<Akte> akteObtained = akteRepository.findById(akteId);
        if (akteObtained.isPresent()) {
            Akte akte = akteObtained.get();
            akte.setInPapierKorb(papierKorb);
            return convertModelToDTO(akteRepository.save(akte));

        } else {
            throw new Exception("AKTE ID TO BE TEMPORARY DELETED OR RESTORED NOT FOUND EXCEPTION :::" + akteId);

        }
    }

    @Override
    public String deleteMultipleAktenPermanently(List<Long> akteIdList) throws Exception {
        for (Long akteId : akteIdList) {
            Optional<Akte> akteObtained = akteRepository.findById(akteId);
            if (akteObtained.isPresent()) {
                akteRepository.delete(akteObtained.get());

            }
            else {
                throw new Exception("AKTE ID TO BE DELETED NOT FOUND EXCEPTION :::" + akteId);

            }
        }

        return "successfully deleted all akten";

    }

    @Override
    public List<AkteDTO> findAktenByFiltering(Long heftnummer, String flurStueck) {
        List<Akte> akteList = new ArrayList<Akte>(akteRepository.findAkteByFiltering(heftnummer,flurStueck));
        List<AkteDTO> akteDTOList= new ArrayList<>();
        for(Akte akte: akteList){
            akteDTOList.add(convertModelToDTO(akte));

        }
        return akteDTOList;
    }

}