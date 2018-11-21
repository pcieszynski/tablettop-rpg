package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.GlovesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gloves and its DTO GlovesDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface GlovesMapper extends EntityMapper<GlovesDTO, Gloves> {


    @Mapping(target = "characters", ignore = true)
    Gloves toEntity(GlovesDTO glovesDTO);

    default Gloves fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gloves gloves = new Gloves();
        gloves.setId(id);
        return gloves;
    }
}
