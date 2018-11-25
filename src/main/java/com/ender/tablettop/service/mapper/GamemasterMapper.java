package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.GamemasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gamemaster and its DTO GamemasterDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface GamemasterMapper extends EntityMapper<GamemasterDTO, Gamemaster> {

    @Mapping(source = "player", target = "playerId")
    GamemasterDTO toDto(Gamemaster gamemaster);

    @Mapping(target = "games", ignore = true)
    @Mapping(source = "playerId", target = "player")
    Gamemaster toEntity(GamemasterDTO gamemasterDTO);

    default Gamemaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gamemaster gamemaster = new Gamemaster();
        gamemaster.setId(id);
        return gamemaster;
    }
}
