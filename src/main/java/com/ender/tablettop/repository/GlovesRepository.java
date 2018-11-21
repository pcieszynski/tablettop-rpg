package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Gloves;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Gloves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlovesRepository extends JpaRepository<Gloves, Long> {

    @Query(value = "select distinct gloves from Gloves gloves left join fetch gloves.shops",
        countQuery = "select count(distinct gloves) from Gloves gloves")
    Page<Gloves> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct gloves from Gloves gloves left join fetch gloves.shops")
    List<Gloves> findAllWithEagerRelationships();

    @Query("select gloves from Gloves gloves left join fetch gloves.shops where gloves.id =:id")
    Optional<Gloves> findOneWithEagerRelationships(@Param("id") Long id);

}
