package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select distinct game from Game game left join fetch game.players",
        countQuery = "select count(distinct game) from Game game")
    Page<Game> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct game from Game game left join fetch game.players")
    List<Game> findAllWithEagerRelationships();

    @Query("select game from Game game left join fetch game.players where game.id =:id")
    Optional<Game> findOneWithEagerRelationships(@Param("id") Long id);

}
