package com.softwareone.postacademy.repository;

import com.softwareone.postacademy.model.GrundstuecksInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrundstuecksBezeichnungRepository extends JpaRepository<GrundstuecksInformation, Long> {
}
