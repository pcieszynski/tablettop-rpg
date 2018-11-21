package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Battle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Battle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    @Query(value = "select distinct battle from Battle battle left join fetch battle.monsters",
        countQuery = "select count(distinct battle) from Battle battle")
    Page<Battle> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct battle from Battle battle left join fetch battle.monsters")
    List<Battle> findAllWithEagerRelationships();

    @Query("select battle from Battle battle left join fetch battle.monsters where battle.id =:id")
    Optional<Battle> findOneWithEagerRelationships(@Param("id") Long id);

}
