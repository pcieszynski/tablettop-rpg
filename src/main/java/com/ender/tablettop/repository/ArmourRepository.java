package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Armour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Armour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmourRepository extends JpaRepository<Armour, Long> {

    @Query(value = "select distinct armour from Armour armour left join fetch armour.shops",
        countQuery = "select count(distinct armour) from Armour armour")
    Page<Armour> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct armour from Armour armour left join fetch armour.shops")
    List<Armour> findAllWithEagerRelationships();

    @Query("select armour from Armour armour left join fetch armour.shops where armour.id =:id")
    Optional<Armour> findOneWithEagerRelationships(@Param("id") Long id);

}
