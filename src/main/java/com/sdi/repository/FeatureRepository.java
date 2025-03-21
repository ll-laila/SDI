package com.sdi.repository;

import com.sdi.domain.Feature;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Feature entity.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    default Optional<Feature> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Feature> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Feature> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select feature from Feature feature left join fetch feature.module",
        countQuery = "select count(feature) from Feature feature"
    )
    Page<Feature> findAllWithToOneRelationships(Pageable pageable);

    @Query("select feature from Feature feature left join fetch feature.module")
    List<Feature> findAllWithToOneRelationships();

    @Query("select feature from Feature feature left join fetch feature.module where feature.id =:id")
    Optional<Feature> findOneWithToOneRelationships(@Param("id") Long id);
}
