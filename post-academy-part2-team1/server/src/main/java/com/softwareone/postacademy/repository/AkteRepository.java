package com.softwareone.postacademy.repository;

import com.softwareone.postacademy.model.Akte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public interface AkteRepository extends JpaRepository<Akte, Long> {

    @Query(value = "select max(d.neueHeftnummer) from #{#entityName} d ")
    Long findLastHeftnummer();

    @Query(value = "select d from #{#entityName} d where d.inPapierKorb=false")
    List<Akte> findAllAkten();

    @Query(value = "select d from #{#entityName} d where d.inPapierKorb=true ")
    List<Akte> findAllAktenFromPapierKorb();

    @Query(value = "select d from #{#entityName} d where d.inPapierKorb=true and d.akteId= :akteid")
    Optional<Akte> findAkteFromPapierkorbByid(Long akteid);

    @Query(value = "SELECT a FROM Akte a JOIN a.allGrundstuecksInformationen g " +
            "WHERE ( :flurStueck is null or g.flurStueck LIKE %:flurStueck% ) and" +
            " (:heftnummer is null or a.neueHeftnummer=:heftnummer ) and" +
            "(:stadtBezirk is null or a.stadtBezirk=:stadtBezirk ) and" +
            "(:kennZiffer is null or a.kennZiffer=:kennZiffer ) and" +
            "(:freiText is null or a.sonstigeAnmerkungen LIKE %:freiText% or g.anmerkung LIKE %:freiText% ) and" +
            "(a.inPapierKorb = false ) and"+
            "(:flur is null or g.flur=:flur )order by a.kennZiffer, a.stadtBezirk,a.neueHeftnummer ")
    LinkedHashSet<Akte> findAkteByFiltering(@Param("heftnummer")Long heftnummer,@Param("flurStueck")String flurStueck,
                                                   @Param("stadtBezirk") Long stadtBezirk, @Param("kennZiffer") Long kennZiffer,
                                                   @Param("flur") Long flur, @Param("freiText") String freiText);






}
