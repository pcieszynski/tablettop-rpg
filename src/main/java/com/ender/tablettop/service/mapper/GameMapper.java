package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.GameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, GamemasterMapper.class})
public interface GameMapper extends EntityMapper<GameDTO, Game> {

    @Mapping(source = "gamemaster.id", target = "gamemasterId")
    GameDTO toDto(Game game);

    @Mapping(target = "events", ignore = true)
    @Mapping(source = "gamemasterId", target = "gamemaster")
    @Mapping(target = "characters", ignore = true)
    Game toEntity(GameDTO gameDTO);

    default Game fromId(Long id) {
        if (id == null) {
            return null;
        }
        Game game = new Game();
        game.setId(id);
        return game;
    }
}
