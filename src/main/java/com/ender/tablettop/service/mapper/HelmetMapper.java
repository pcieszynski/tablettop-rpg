package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.HelmetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Helmet and its DTO HelmetDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface HelmetMapper extends EntityMapper<HelmetDTO, Helmet> {


    @Mapping(target = "characters", ignore = true)
    Helmet toEntity(HelmetDTO helmetDTO);

    default Helmet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Helmet helmet = new Helmet();
        helmet.setId(id);
        return helmet;
    }
}
