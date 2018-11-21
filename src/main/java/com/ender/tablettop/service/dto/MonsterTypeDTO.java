package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MonsterType entity.
 */
public class MonsterTypeDTO implements Serializable {

    private Long id;

    private String name;

    private Integer strength;

    private Integer agility;

    private Integer constituition;

    private Integer intelligence;

    private Integer willpower;

    private Integer charisma;

    private Integer hitpoints;

    private String attack;

    private Integer defense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getConstituition() {
        return constituition;
    }

    public void setConstituition(Integer constituition) {
        this.constituition = constituition;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getWillpower() {
        return willpower;
    }

    public void setWillpower(Integer willpower) {
        this.willpower = willpower;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(Integer hitpoints) {
        this.hitpoints = hitpoints;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonsterTypeDTO monsterTypeDTO = (MonsterTypeDTO) o;
        if (monsterTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monsterTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonsterTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", strength=" + getStrength() +
            ", agility=" + getAgility() +
            ", constituition=" + getConstituition() +
            ", intelligence=" + getIntelligence() +
            ", willpower=" + getWillpower() +
            ", charisma=" + getCharisma() +
            ", hitpoints=" + getHitpoints() +
            ", attack='" + getAttack() + "'" +
            ", defense=" + getDefense() +
            "}";
    }
}
