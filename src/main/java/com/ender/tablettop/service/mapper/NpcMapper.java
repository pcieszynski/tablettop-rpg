package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.NpcDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Npc and its DTO NpcDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface NpcMapper extends EntityMapper<NpcDTO, Npc> {

    @Mapping(source = "event.id", target = "eventId")
    NpcDTO toDto(Npc npc);

    @Mapping(source = "eventId", target = "event")
    Npc toEntity(NpcDTO npcDTO);

    default Npc fromId(Long id) {
        if (id == null) {
            return null;
        }
        Npc npc = new Npc();
        npc.setId(id);
        return npc;
    }
}
