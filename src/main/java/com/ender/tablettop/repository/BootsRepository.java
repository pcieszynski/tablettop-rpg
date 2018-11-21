package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Boots;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Boots entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BootsRepository extends JpaRepository<Boots, Long> {

    @Query(value = "select distinct boots from Boots boots left join fetch boots.shops",
        countQuery = "select count(distinct boots) from Boots boots")
    Page<Boots> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct boots from Boots boots left join fetch boots.shops")
    List<Boots> findAllWithEagerRelationships();

    @Query("select boots from Boots boots left join fetch boots.shops where boots.id =:id")
    Optional<Boots> findOneWithEagerRelationships(@Param("id") Long id);

}
