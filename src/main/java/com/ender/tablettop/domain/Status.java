package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "effect")
    private String effect;

    @ManyToMany(mappedBy = "statuses")
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

    public Status name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public Status effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Status characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Status addCharacter(Character character) {
        this.characters.add(character);
        character.getStatuses().add(this);
        return this;
    }

    public Status removeCharacter(Character character) {
        this.characters.remove(character);
        character.getStatuses().remove(this);
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
        Status status = (Status) o;
        if (status.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), status.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            "}";
    }
}
