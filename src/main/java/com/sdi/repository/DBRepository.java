package com.sdi.repository;

import com.sdi.domain.DB;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DBRepository extends JpaRepository<DB, Long> {}
