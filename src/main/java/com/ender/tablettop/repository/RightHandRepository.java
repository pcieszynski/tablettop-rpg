package com.ender.tablettop.repository;

import com.ender.tablettop.domain.RightHand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RightHand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RightHandRepository extends JpaRepository<RightHand, Long> {

    @Query(value = "select distinct right_hand from RightHand right_hand left join fetch right_hand.shops",
        countQuery = "select count(distinct right_hand) from RightHand right_hand")
    Page<RightHand> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct right_hand from RightHand right_hand left join fetch right_hand.shops")
    List<RightHand> findAllWithEagerRelationships();

    @Query("select right_hand from RightHand right_hand left join fetch right_hand.shops where right_hand.id =:id")
    Optional<RightHand> findOneWithEagerRelationships(@Param("id") Long id);

}
