package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {


    @Mapping(target = "shops", ignore = true)
    @Mapping(target = "characters", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
