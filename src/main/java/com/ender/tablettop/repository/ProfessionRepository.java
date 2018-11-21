package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Profession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Profession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {

    @Query(value = "select distinct profession from Profession profession left join fetch profession.skills",
        countQuery = "select count(distinct profession) from Profession profession")
    Page<Profession> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct profession from Profession profession left join fetch profession.skills")
    List<Profession> findAllWithEagerRelationships();

    @Query("select profession from Profession profession left join fetch profession.skills where profession.id =:id")
    Optional<Profession> findOneWithEagerRelationships(@Param("id") Long id);

}
