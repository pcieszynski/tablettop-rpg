package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Character entity.
 */
public class CharacterDTO implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private Integer experience;

    private Integer maxHitpoints;

    private Integer currentHitpoints;

    private Integer gold;

    private Integer strength;

    private Integer agility;

    private Integer constituition;

    private Integer intelligence;

    private Integer willpower;

    private Integer charisma;

    private Set<SkillDTO> skills = new HashSet<>();

    private Set<GameDTO> games = new HashSet<>();

    private Set<StatusDTO> statuses = new HashSet<>();

    private Set<ItemDTO> items = new HashSet<>();

    private Long professionId;

    private Long playerId;

    private Long helmetId;

    private Long armourId;

    private Long bootsId;

    private Long glovesId;

    private Long legsId;

    private Long rightHandId;

    private Long leftHandId;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(Integer maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public Integer getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(Integer currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
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

    public Set<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }

    public Set<GameDTO> getGames() {
        return games;
    }

    public void setGames(Set<GameDTO> games) {
        this.games = games;
    }

    public Set<StatusDTO> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<StatusDTO> statuses) {
        this.statuses = statuses;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemDTO> items) {
        this.items = items;
    }

    public Long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Long professionId) {
        this.professionId = professionId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getHelmetId() {
        return helmetId;
    }

    public void setHelmetId(Long helmetId) {
        this.helmetId = helmetId;
    }

    public Long getArmourId() {
        return armourId;
    }

    public void setArmourId(Long armourId) {
        this.armourId = armourId;
    }

    public Long getBootsId() {
        return bootsId;
    }

    public void setBootsId(Long bootsId) {
        this.bootsId = bootsId;
    }

    public Long getGlovesId() {
        return glovesId;
    }

    public void setGlovesId(Long glovesId) {
        this.glovesId = glovesId;
    }

    public Long getLegsId() {
        return legsId;
    }

    public void setLegsId(Long legsId) {
        this.legsId = legsId;
    }

    public Long getRightHandId() {
        return rightHandId;
    }

    public void setRightHandId(Long rightHandId) {
        this.rightHandId = rightHandId;
    }

    public Long getLeftHandId() {
        return leftHandId;
    }

    public void setLeftHandId(Long leftHandId) {
        this.leftHandId = leftHandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CharacterDTO characterDTO = (CharacterDTO) o;
        if (characterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), characterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CharacterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            ", experience=" + getExperience() +
            ", maxHitpoints=" + getMaxHitpoints() +
            ", currentHitpoints=" + getCurrentHitpoints() +
            ", gold=" + getGold() +
            ", strength=" + getStrength() +
            ", agility=" + getAgility() +
            ", constituition=" + getConstituition() +
            ", intelligence=" + getIntelligence() +
            ", willpower=" + getWillpower() +
            ", charisma=" + getCharisma() +
            ", profession=" + getProfessionId() +
            ", player=" + getPlayerId() +
            ", helmet=" + getHelmetId() +
            ", armour=" + getArmourId() +
            ", boots=" + getBootsId() +
            ", gloves=" + getGlovesId() +
            ", legs=" + getLegsId() +
            ", rightHand=" + getRightHandId() +
            ", leftHand=" + getLeftHandId() +
            "}";
    }
}
