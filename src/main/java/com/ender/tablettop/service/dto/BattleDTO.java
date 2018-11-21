package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Battle entity.
 */
public class BattleDTO implements Serializable {

    private Long id;

    private String monster;

    private Integer monsterNumber;

    private Set<MonsterDTO> monsters = new HashSet<>();

    private Long eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonster() {
        return monster;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }

    public Integer getMonsterNumber() {
        return monsterNumber;
    }

    public void setMonsterNumber(Integer monsterNumber) {
        this.monsterNumber = monsterNumber;
    }

    public Set<MonsterDTO> getMonsters() {
        return monsters;
    }

    public void setMonsters(Set<MonsterDTO> monsters) {
        this.monsters = monsters;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BattleDTO battleDTO = (BattleDTO) o;
        if (battleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), battleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BattleDTO{" +
            "id=" + getId() +
            ", monster='" + getMonster() + "'" +
            ", monsterNumber=" + getMonsterNumber() +
            ", event=" + getEventId() +
            "}";
    }
}
