package com.softwareone.postacademy.repository;

import com.softwareone.postacademy.model.Akte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

public interface AkteRepository extends JpaRepository<Akte, Long> {

    @Query(value = "select max(d.neueHeftnummer) from #{#entityName} d ")
    Long findLastHeftnummer();

    @Query(value = "select d from #{#entityName} d where d.inPapierKorb=false")
    List<Akte> findAllAkten();

    @Query(value = "select d from #{#entityName} d where d.inPapierKorb=true ")
    List<Akte> findAllAktenFromPapierKorb();

    @Query(value = "SELECT a FROM Akte a JOIN a.allGrundstuecksInformationen g WHERE g.flurStueck LIKE %:flurStueck%  and" +
            "  a.neueHeftnummer=:heftnummer order by a.neueHeftnummer")
    public LinkedHashSet<Akte> findAkteByFiltering(Long heftnummer,String flurStueck);






}
