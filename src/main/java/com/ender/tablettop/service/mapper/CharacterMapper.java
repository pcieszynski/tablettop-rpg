package com.ender.tablettop.service.mapper;

import com.ender.tablettop.domain.*;
import com.ender.tablettop.service.dto.CharacterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Character and its DTO CharacterDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class, GameMapper.class, StatusMapper.class, ItemMapper.class, ProfessionMapper.class, PlayerMapper.class, HelmetMapper.class, ArmourMapper.class, BootsMapper.class, GlovesMapper.class, LegsMapper.class, RightHandMapper.class, LeftHandMapper.class})
public interface CharacterMapper extends EntityMapper<CharacterDTO, Character> {

    @Mapping(source = "profession.id", target = "professionId")
    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "helmet.id", target = "helmetId")
    @Mapping(source = "armour.id", target = "armourId")
    @Mapping(source = "boots.id", target = "bootsId")
    @Mapping(source = "gloves.id", target = "glovesId")
    @Mapping(source = "legs.id", target = "legsId")
    @Mapping(source = "rightHand.id", target = "rightHandId")
    @Mapping(source = "leftHand.id", target = "leftHandId")
    CharacterDTO toDto(Character character);

    @Mapping(source = "professionId", target = "profession")
    @Mapping(source = "playerId", target = "player")
    @Mapping(source = "helmetId", target = "helmet")
    @Mapping(source = "armourId", target = "armour")
    @Mapping(source = "bootsId", target = "boots")
    @Mapping(source = "glovesId", target = "gloves")
    @Mapping(source = "legsId", target = "legs")
    @Mapping(source = "rightHandId", target = "rightHand")
    @Mapping(source = "leftHandId", target = "leftHand")
    Character toEntity(CharacterDTO characterDTO);

    default Character fromId(Long id) {
        if (id == null) {
            return null;
        }
        Character character = new Character();
        character.setId(id);
        return character;
    }
}
