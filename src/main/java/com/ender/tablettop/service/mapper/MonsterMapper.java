package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.MonsterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Monster and its DTO MonsterDTO.
 */
@Mapper(componentModel = "spring", uses = {MonsterTypeMapper.class})
public interface MonsterMapper extends EntityMapper<MonsterDTO, Monster> {

    @Mapping(source = "type.id", target = "typeId")
    MonsterDTO toDto(Monster monster);

    @Mapping(source = "typeId", target = "type")
    @Mapping(target = "battles", ignore = true)
    Monster toEntity(MonsterDTO monsterDTO);

    default Monster fromId(Long id) {
        if (id == null) {
            return null;
        }
        Monster monster = new Monster();
        monster.setId(id);
        return monster;
    }
}
