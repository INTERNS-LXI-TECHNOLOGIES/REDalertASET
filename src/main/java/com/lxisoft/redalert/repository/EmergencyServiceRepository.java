package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.EmergencyService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmergencyService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergencyServiceRepository extends JpaRepository<EmergencyService, Long> {

}
