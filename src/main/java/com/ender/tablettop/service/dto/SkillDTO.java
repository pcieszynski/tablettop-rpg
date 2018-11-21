package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Skill entity.
 */
public class SkillDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String description;

    private String damage;

    private Integer heal;

    private Integer level;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public Integer getHeal() {
        return heal;
    }

    public void setHeal(Integer heal) {
        this.heal = heal;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (skillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", damage='" + getDamage() + "'" +
            ", heal=" + getHeal() +
            ", level=" + getLevel() +
            "}";
    }
}
