package com.sdi.repository;

import com.sdi.domain.OS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OSRepository extends JpaRepository<OS, Long> {}
