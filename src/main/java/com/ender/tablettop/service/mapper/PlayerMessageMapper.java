package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.PlayerMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PlayerMessage and its DTO PlayerMessageDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, EventMapper.class})
public interface PlayerMessageMapper extends EntityMapper<PlayerMessageDTO, PlayerMessage> {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "event.id", target = "eventId")
    PlayerMessageDTO toDto(PlayerMessage playerMessage);

    @Mapping(source = "playerId", target = "player")
    @Mapping(source = "eventId", target = "event")
    PlayerMessage toEntity(PlayerMessageDTO playerMessageDTO);

    default PlayerMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setId(id);
        return playerMessage;
    }
}
