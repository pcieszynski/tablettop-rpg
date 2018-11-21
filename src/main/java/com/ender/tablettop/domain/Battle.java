package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Battle.
 */
@Entity
@Table(name = "battle")
public class Battle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "monster")
    private String monster;

    @Column(name = "monster_number")
    private Integer monsterNumber;

    @ManyToMany
    @JoinTable(name = "battle_monster",
               joinColumns = @JoinColumn(name = "battles_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "monsters_id", referencedColumnName = "id"))
    private Set<Monster> monsters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("battles")
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonster() {
        return monster;
    }

    public Battle monster(String monster) {
        this.monster = monster;
        return this;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }

    public Integer getMonsterNumber() {
        return monsterNumber;
    }

    public Battle monsterNumber(Integer monsterNumber) {
        this.monsterNumber = monsterNumber;
        return this;
    }

    public void setMonsterNumber(Integer monsterNumber) {
        this.monsterNumber = monsterNumber;
    }

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public Battle monsters(Set<Monster> monsters) {
        this.monsters = monsters;
        return this;
    }

    public Battle addMonster(Monster monster) {
        this.monsters.add(monster);
        monster.getBattles().add(this);
        return this;
    }

    public Battle removeMonster(Monster monster) {
        this.monsters.remove(monster);
        monster.getBattles().remove(this);
        return this;
    }

    public void setMonsters(Set<Monster> monsters) {
        this.monsters = monsters;
    }

    public Event getEvent() {
        return event;
    }

    public Battle event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        Battle battle = (Battle) o;
        if (battle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), battle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Battle{" +
            "id=" + getId() +
            ", monster='" + getMonster() + "'" +
            ", monsterNumber=" + getMonsterNumber() +
            "}";
    }
}
