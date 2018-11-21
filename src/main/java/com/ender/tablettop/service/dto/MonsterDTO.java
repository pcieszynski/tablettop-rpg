package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Monster entity.
 */
public class MonsterDTO implements Serializable {

    private Long id;

    private Integer currentHP;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long monsterTypeId) {
        this.typeId = monsterTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonsterDTO monsterDTO = (MonsterDTO) o;
        if (monsterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monsterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonsterDTO{" +
            "id=" + getId() +
            ", currentHP=" + getCurrentHP() +
            ", type=" + getTypeId() +
            "}";
    }
}
