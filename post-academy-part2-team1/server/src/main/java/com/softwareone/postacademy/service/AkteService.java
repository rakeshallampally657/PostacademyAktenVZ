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
}