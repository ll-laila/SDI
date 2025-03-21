package com.sdi.repository;

import com.sdi.domain.Module;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Module entity.
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    default Optional<Module> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Module> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Module> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select module from Module module left join fetch module.product left join fetch module.domaine",
        countQuery = "select count(module) from Module module"
    )
    Page<Module> findAllWithToOneRelationships(Pageable pageable);

    @Query("select module from Module module left join fetch module.product left join fetch module.domaine")
    List<Module> findAllWithToOneRelationships();

    @Query("select module from Module module left join fetch module.product left join fetch module.domaine where module.id =:id")
    Optional<Module> findOneWithToOneRelationships(@Param("id") Long id);
}
