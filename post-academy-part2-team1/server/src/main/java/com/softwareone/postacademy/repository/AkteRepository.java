package com.softwareone.postacademy.repository;

import com.softwareone.postacademy.model.Akte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AkteRepository extends JpaRepository<Akte, Long> {

    @Query(value = "select max(d.neueHeftnummer) from #{#entityName} d ")
    Long findLastHeftnummer();
}
