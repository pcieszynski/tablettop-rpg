package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.BootsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Boots and its DTO BootsDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface BootsMapper extends EntityMapper<BootsDTO, Boots> {


    @Mapping(target = "characters", ignore = true)
    Boots toEntity(BootsDTO bootsDTO);

    default Boots fromId(Long id) {
        if (id == null) {
            return null;
        }
        Boots boots = new Boots();
        boots.setId(id);
        return boots;
    }
}
