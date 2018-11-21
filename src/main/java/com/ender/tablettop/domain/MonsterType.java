package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MonsterType.
 */
@Entity
@Table(name = "monster_type")
public class MonsterType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "agility")
    private Integer agility;

    @Column(name = "constituition")
    private Integer constituition;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "willpower")
    private Integer willpower;

    @Column(name = "charisma")
    private Integer charisma;

    @Column(name = "hitpoints")
    private Integer hitpoints;

    @Column(name = "attack")
    private String attack;

    @Column(name = "defense")
    private Integer defense;

    @OneToMany(mappedBy = "type")
    private Set<Monster> monsters = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MonsterType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrength() {
        return strength;
    }

    public MonsterType strength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public MonsterType agility(Integer agility) {
        this.agility = agility;
        return this;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getConstituition() {
        return constituition;
    }

    public MonsterType constituition(Integer constituition) {
        this.constituition = constituition;
        return this;
    }

    public void setConstituition(Integer constituition) {
        this.constituition = constituition;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public MonsterType intelligence(Integer intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getWillpower() {
        return willpower;
    }

    public MonsterType willpower(Integer willpower) {
        this.willpower = willpower;
        return this;
    }

    public void setWillpower(Integer willpower) {
        this.willpower = willpower;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public MonsterType charisma(Integer charisma) {
        this.charisma = charisma;
        return this;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getHitpoints() {
        return hitpoints;
    }

    public MonsterType hitpoints(Integer hitpoints) {
        this.hitpoints = hitpoints;
        return this;
    }

    public void setHitpoints(Integer hitpoints) {
        this.hitpoints = hitpoints;
    }

    public String getAttack() {
        return attack;
    }

    public MonsterType attack(String attack) {
        this.attack = attack;
        return this;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public MonsterType defense(Integer defense) {
        this.defense = defense;
        return this;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public MonsterType monsters(Set<Monster> monsters) {
        this.monsters = monsters;
        return this;
    }

    public MonsterType addMonster(Monster monster) {
        this.monsters.add(monster);
        monster.setType(this);
        return this;
    }

    public MonsterType removeMonster(Monster monster) {
        this.monsters.remove(monster);
        monster.setType(null);
        return this;
    }

    public void setMonsters(Set<Monster> monsters) {
        this.monsters = monsters;
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
        MonsterType monsterType = (MonsterType) o;
        if (monsterType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monsterType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonsterType{" +
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
