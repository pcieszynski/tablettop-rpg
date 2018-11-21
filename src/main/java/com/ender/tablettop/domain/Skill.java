package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "damage")
    private String damage;

    @Column(name = "heal")
    private Integer heal;

    @Column(name = "jhi_level")
    private Integer level;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Profession> professions = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Character> characters = new HashSet<>();

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

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Skill description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamage() {
        return damage;
    }

    public Skill damage(String damage) {
        this.damage = damage;
        return this;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public Integer getHeal() {
        return heal;
    }

    public Skill heal(Integer heal) {
        this.heal = heal;
        return this;
    }

    public void setHeal(Integer heal) {
        this.heal = heal;
    }

    public Integer getLevel() {
        return level;
    }

    public Skill level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Profession> getProfessions() {
        return professions;
    }

    public Skill professions(Set<Profession> professions) {
        this.professions = professions;
        return this;
    }

    public Skill addProfession(Profession profession) {
        this.professions.add(profession);
        profession.getSkills().add(this);
        return this;
    }

    public Skill removeProfession(Profession profession) {
        this.professions.remove(profession);
        profession.getSkills().remove(this);
        return this;
    }

    public void setProfessions(Set<Profession> professions) {
        this.professions = professions;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Skill characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Skill addCharacter(Character character) {
        this.characters.add(character);
        character.getSkills().add(this);
        return this;
    }

    public Skill removeCharacter(Character character) {
        this.characters.remove(character);
        character.getSkills().remove(this);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
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
        Skill skill = (Skill) o;
        if (skill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", damage='" + getDamage() + "'" +
            ", heal=" + getHeal() +
            ", level=" + getLevel() +
            "}";
    }
}
