package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.RightHandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RightHand and its DTO RightHandDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface RightHandMapper extends EntityMapper<RightHandDTO, RightHand> {


    @Mapping(target = "characters", ignore = true)
    RightHand toEntity(RightHandDTO rightHandDTO);

    default RightHand fromId(Long id) {
        if (id == null) {
            return null;
        }
        RightHand rightHand = new RightHand();
        rightHand.setId(id);
        return rightHand;
    }
}
