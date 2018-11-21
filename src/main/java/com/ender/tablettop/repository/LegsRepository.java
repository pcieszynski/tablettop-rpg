package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Legs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Legs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegsRepository extends JpaRepository<Legs, Long> {

    @Query(value = "select distinct legs from Legs legs left join fetch legs.shops",
        countQuery = "select count(distinct legs) from Legs legs")
    Page<Legs> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct legs from Legs legs left join fetch legs.shops")
    List<Legs> findAllWithEagerRelationships();

    @Query("select legs from Legs legs left join fetch legs.shops where legs.id =:id")
    Optional<Legs> findOneWithEagerRelationships(@Param("id") Long id);

}
