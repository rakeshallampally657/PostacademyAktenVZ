package com.softwareone.postacademy.service;

import com.softwareone.postacademy.dto.AkteDTO;
import com.softwareone.postacademy.model.Akte;

import java.util.List;

public interface AkteService {
    AkteDTO addAkte(AkteDTO akteDTO);
    List<AkteDTO> getAllAkte();
    AkteDTO fetchAkteById(Long akteId) throws Exception;
    Long findLastHeftNumber();
    AkteDTO updateAkte(AkteDTO akteDTO) throws Exception;
    AkteDTO hardDeleteAkteById(Long akteId) throws Exception;
    void deleteAllAkten();
    List<AkteDTO> getAllAktenFromPapierkorb();
    AkteDTO temporaryDeletionAndRestore(Long akteId, boolean papierKorb) throws Exception;
    String deleteMultipleAktenPermanently(List<Long> akteIdList) throws Exception;
    List<AkteDTO> findAktenByFiltering(Long heftnummer,String flurStueck,Long stadtBezirk,Long kennZiffer, Long flur,String freiText);
    List<AkteDTO> restoreMultipleAktenFromPapierkorb(List<Long> akteIdList) throws Exception;


}