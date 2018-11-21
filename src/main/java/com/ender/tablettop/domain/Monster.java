package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Monster.
 */
@Entity
@Table(name = "monster")
public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "current_hp")
    private Integer currentHP;

    @ManyToOne
    @JsonIgnoreProperties("monsters")
    private MonsterType type;

    @ManyToMany(mappedBy = "monsters")
    @JsonIgnore
    private Set<Battle> battles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public Monster currentHP(Integer currentHP) {
        this.currentHP = currentHP;
        return this;
    }

    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
    }

    public MonsterType getType() {
        return type;
    }

    public Monster type(MonsterType monsterType) {
        this.type = monsterType;
        return this;
    }

    public void setType(MonsterType monsterType) {
        this.type = monsterType;
    }

    public Set<Battle> getBattles() {
        return battles;
    }

    public Monster battles(Set<Battle> battles) {
        this.battles = battles;
        return this;
    }

    public Monster addBattle(Battle battle) {
        this.battles.add(battle);
        battle.getMonsters().add(this);
        return this;
    }

    public Monster removeBattle(Battle battle) {
        this.battles.remove(battle);
        battle.getMonsters().remove(this);
        return this;
    }

    public void setBattles(Set<Battle> battles) {
        this.battles = battles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Monster monster = (Monster) o;
        if (monster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monster{" +
            "id=" + getId() +
            ", currentHP=" + getCurrentHP() +
            "}";
    }
}
