package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.LeftHandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeftHand and its DTO LeftHandDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface LeftHandMapper extends EntityMapper<LeftHandDTO, LeftHand> {


    @Mapping(target = "characters", ignore = true)
    LeftHand toEntity(LeftHandDTO leftHandDTO);

    default LeftHand fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeftHand leftHand = new LeftHand();
        leftHand.setId(id);
        return leftHand;
    }
}
