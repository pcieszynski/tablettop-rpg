package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Profession entity.
 */
public class ProfessionDTO implements Serializable {

    private Long id;

    private String name;

    private Integer strengthModifier;

    private Integer agilityModifier;

    private Integer constituitionModifier;

    private Integer intelligenceModifier;

    private Integer willpowerModifier;

    private Integer charismaModifier;

    private Set<SkillDTO> skills = new HashSet<>();

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

    public Integer getStrengthModifier() {
        return strengthModifier;
    }

    public void setStrengthModifier(Integer strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public Integer getAgilityModifier() {
        return agilityModifier;
    }

    public void setAgilityModifier(Integer agilityModifier) {
        this.agilityModifier = agilityModifier;
    }

    public Integer getConstituitionModifier() {
        return constituitionModifier;
    }

    public void setConstituitionModifier(Integer constituitionModifier) {
        this.constituitionModifier = constituitionModifier;
    }

    public Integer getIntelligenceModifier() {
        return intelligenceModifier;
    }

    public void setIntelligenceModifier(Integer intelligenceModifier) {
        this.intelligenceModifier = intelligenceModifier;
    }

    public Integer getWillpowerModifier() {
        return willpowerModifier;
    }

    public void setWillpowerModifier(Integer willpowerModifier) {
        this.willpowerModifier = willpowerModifier;
    }

    public Integer getCharismaModifier() {
        return charismaModifier;
    }

    public void setCharismaModifier(Integer charismaModifier) {
        this.charismaModifier = charismaModifier;
    }

    public Set<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionDTO professionDTO = (ProfessionDTO) o;
        if (professionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", strengthModifier=" + getStrengthModifier() +
            ", agilityModifier=" + getAgilityModifier() +
            ", constituitionModifier=" + getConstituitionModifier() +
            ", intelligenceModifier=" + getIntelligenceModifier() +
            ", willpowerModifier=" + getWillpowerModifier() +
            ", charismaModifier=" + getCharismaModifier() +
            "}";
    }
}
