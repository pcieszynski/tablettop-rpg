package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Helmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Helmet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HelmetRepository extends JpaRepository<Helmet, Long> {

    @Query(value = "select distinct helmet from Helmet helmet left join fetch helmet.shops",
        countQuery = "select count(distinct helmet) from Helmet helmet")
    Page<Helmet> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct helmet from Helmet helmet left join fetch helmet.shops")
    List<Helmet> findAllWithEagerRelationships();

    @Query("select helmet from Helmet helmet left join fetch helmet.shops where helmet.id =:id")
    Optional<Helmet> findOneWithEagerRelationships(@Param("id") Long id);

}
