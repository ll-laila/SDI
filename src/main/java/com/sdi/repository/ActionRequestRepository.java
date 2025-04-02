package com.sdi.repository;

import com.sdi.domain.ActionRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActionRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRequestRepository extends JpaRepository<ActionRequest, Long> {}
