package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.ShopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shop and its DTO ShopDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ShopMapper extends EntityMapper<ShopDTO, Shop> {


    @Mapping(target = "events", ignore = true)
    @Mapping(target = "helmets", ignore = true)
    @Mapping(target = "armours", ignore = true)
    @Mapping(target = "boots", ignore = true)
    @Mapping(target = "gloves", ignore = true)
    @Mapping(target = "legs", ignore = true)
    @Mapping(target = "rightHands", ignore = true)
    @Mapping(target = "leftHands", ignore = true)
    Shop toEntity(ShopDTO shopDTO);

    default Shop fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }
}
