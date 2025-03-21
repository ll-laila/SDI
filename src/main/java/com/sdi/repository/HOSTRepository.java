package com.sdi.repository;

import com.sdi.domain.HOST;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HOST entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HOSTRepository extends JpaRepository<HOST, Long> {}
