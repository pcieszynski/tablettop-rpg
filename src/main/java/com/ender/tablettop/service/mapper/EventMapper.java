package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {GameMapper.class, ShopMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "shop.id", target = "shopId")
    EventDTO toDto(Event event);

    @Mapping(target = "npcs", ignore = true)
    @Mapping(target = "battles", ignore = true)
    @Mapping(target = "playerMessages", ignore = true)
    @Mapping(source = "gameId", target = "game")
    @Mapping(source = "shopId", target = "shop")
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
