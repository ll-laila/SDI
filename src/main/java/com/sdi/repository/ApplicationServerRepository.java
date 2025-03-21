package com.sdi.repository;

import com.sdi.domain.ApplicationServer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationServerRepository extends JpaRepository<ApplicationServer, Long> {}
