package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profession.
 */
@Entity
@Table(name = "profession")
public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "strength_modifier")
    private Integer strengthModifier;

    @Column(name = "agility_modifier")
    private Integer agilityModifier;

    @Column(name = "constituition_modifier")
    private Integer constituitionModifier;

    @Column(name = "intelligence_modifier")
    private Integer intelligenceModifier;

    @Column(name = "willpower_modifier")
    private Integer willpowerModifier;

    @Column(name = "charisma_modifier")
    private Integer charismaModifier;

    @OneToMany(mappedBy = "profession")
    private Set<Character> characters = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "profession_skill",
               joinColumns = @JoinColumn(name = "professions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "skills_id", referencedColumnName = "id"))
    private Set<Skill> skills = new HashSet<>();

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

    public Profession name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrengthModifier() {
        return strengthModifier;
    }

    public Profession strengthModifier(Integer strengthModifier) {
        this.strengthModifier = strengthModifier;
        return this;
    }

    public void setStrengthModifier(Integer strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public Integer getAgilityModifier() {
        return agilityModifier;
    }

    public Profession agilityModifier(Integer agilityModifier) {
        this.agilityModifier = agilityModifier;
        return this;
    }

    public void setAgilityModifier(Integer agilityModifier) {
        this.agilityModifier = agilityModifier;
    }

    public Integer getConstituitionModifier() {
        return constituitionModifier;
    }

    public Profession constituitionModifier(Integer constituitionModifier) {
        this.constituitionModifier = constituitionModifier;
        return this;
    }

    public void setConstituitionModifier(Integer constituitionModifier) {
        this.constituitionModifier = constituitionModifier;
    }

    public Integer getIntelligenceModifier() {
        return intelligenceModifier;
    }

    public Profession intelligenceModifier(Integer intelligenceModifier) {
        this.intelligenceModifier = intelligenceModifier;
        return this;
    }

    public void setIntelligenceModifier(Integer intelligenceModifier) {
        this.intelligenceModifier = intelligenceModifier;
    }

    public Integer getWillpowerModifier() {
        return willpowerModifier;
    }

    public Profession willpowerModifier(Integer willpowerModifier) {
        this.willpowerModifier = willpowerModifier;
        return this;
    }

    public void setWillpowerModifier(Integer willpowerModifier) {
        this.willpowerModifier = willpowerModifier;
    }

    public Integer getCharismaModifier() {
        return charismaModifier;
    }

    public Profession charismaModifier(Integer charismaModifier) {
        this.charismaModifier = charismaModifier;
        return this;
    }

    public void setCharismaModifier(Integer charismaModifier) {
        this.charismaModifier = charismaModifier;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Profession characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Profession addCharacter(Character character) {
        this.characters.add(character);
        character.setProfession(this);
        return this;
    }

    public Profession removeCharacter(Character character) {
        this.characters.remove(character);
        character.setProfession(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Profession skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public Profession addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getProfessions().add(this);
        return this;
    }

    public Profession removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getProfessions().remove(this);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
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
        Profession profession = (Profession) o;
        if (profession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profession{" +
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
