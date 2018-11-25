package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Character entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query(value = "select distinct character from Character character left join fetch character.skills left join fetch character.games left join fetch character.statuses left join fetch character.items",
        countQuery = "select count(distinct character) from Character character")
    Page<Character> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct character from Character character left join fetch character.skills left join fetch character.games left join fetch character.statuses left join fetch character.items")
    List<Character> findAllWithEagerRelationships();

    @Query("select character from Character character left join fetch character.skills left join fetch character.games left join fetch character.statuses left join fetch character.items where character.id =:id")
    Optional<Character> findOneWithEagerRelationships(@Param("id") Long id);

}
