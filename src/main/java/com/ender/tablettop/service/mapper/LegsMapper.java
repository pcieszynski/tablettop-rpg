package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.LegsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Legs and its DTO LegsDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface LegsMapper extends EntityMapper<LegsDTO, Legs> {


    @Mapping(target = "characters", ignore = true)
    Legs toEntity(LegsDTO legsDTO);

    default Legs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Legs legs = new Legs();
        legs.setId(id);
        return legs;
    }
}
