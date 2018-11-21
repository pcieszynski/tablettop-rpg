package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.BattleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Battle and its DTO BattleDTO.
 */
@Mapper(componentModel = "spring", uses = {MonsterMapper.class, EventMapper.class})
public interface BattleMapper extends EntityMapper<BattleDTO, Battle> {

    @Mapping(source = "event.id", target = "eventId")
    BattleDTO toDto(Battle battle);

    @Mapping(source = "eventId", target = "event")
    Battle toEntity(BattleDTO battleDTO);

    default Battle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Battle battle = new Battle();
        battle.setId(id);
        return battle;
    }
}
