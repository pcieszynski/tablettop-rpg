package com.ender.tablettop.repository;

import com.ender.tablettop.domain.LeftHand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the LeftHand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeftHandRepository extends JpaRepository<LeftHand, Long> {

    @Query(value = "select distinct left_hand from LeftHand left_hand left join fetch left_hand.shops",
        countQuery = "select count(distinct left_hand) from LeftHand left_hand")
    Page<LeftHand> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct left_hand from LeftHand left_hand left join fetch left_hand.shops")
    List<LeftHand> findAllWithEagerRelationships();

    @Query("select left_hand from LeftHand left_hand left join fetch left_hand.shops where left_hand.id =:id")
    Optional<LeftHand> findOneWithEagerRelationships(@Param("id") Long id);

}
