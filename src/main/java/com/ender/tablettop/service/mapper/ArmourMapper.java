package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.ArmourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Armour and its DTO ArmourDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface ArmourMapper extends EntityMapper<ArmourDTO, Armour> {


    @Mapping(target = "characters", ignore = true)
    Armour toEntity(ArmourDTO armourDTO);

    default Armour fromId(Long id) {
        if (id == null) {
            return null;
        }
        Armour armour = new Armour();
        armour.setId(id);
        return armour;
    }
}
