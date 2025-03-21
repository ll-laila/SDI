package com.sdi.repository;

import com.sdi.domain.HSM;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HSM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HSMRepository extends JpaRepository<HSM, Long> {}
