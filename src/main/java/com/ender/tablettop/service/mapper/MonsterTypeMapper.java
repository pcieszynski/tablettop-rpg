package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.MonsterTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MonsterType and its DTO MonsterTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MonsterTypeMapper extends EntityMapper<MonsterTypeDTO, MonsterType> {


    @Mapping(target = "monsters", ignore = true)
    MonsterType toEntity(MonsterTypeDTO monsterTypeDTO);

    default MonsterType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonsterType monsterType = new MonsterType();
        monsterType.setId(id);
        return monsterType;
    }
}
