package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.ProfessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profession and its DTO ProfessionDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface ProfessionMapper extends EntityMapper<ProfessionDTO, Profession> {


    @Mapping(target = "characters", ignore = true)
    Profession toEntity(ProfessionDTO professionDTO);

    default Profession fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profession profession = new Profession();
        profession.setId(id);
        return profession;
    }
}
