package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gloves.
 */
@Entity
@Table(name = "gloves")
public class Gloves implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "effect")
    private String effect;

    @Column(name = "price")
    private Integer price;

    @Column(name = "defence")
    private Integer defence;

    @Column(name = "part")
    private String part;

    @OneToMany(mappedBy = "gloves")
    private Set<Character> characters = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "gloves_shop",
               joinColumns = @JoinColumn(name = "gloves_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shops_id", referencedColumnName = "id"))
    private Set<Shop> shops = new HashSet<>();

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

    public Gloves name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public Gloves effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public Gloves price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDefence() {
        return defence;
    }

    public Gloves defence(Integer defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public String getPart() {
        return part;
    }

    public Gloves part(String part) {
        this.part = part;
        return this;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Gloves characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Gloves addCharacter(Character character) {
        this.characters.add(character);
        character.setGloves(this);
        return this;
    }

    public Gloves removeCharacter(Character character) {
        this.characters.remove(character);
        character.setGloves(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public Gloves shops(Set<Shop> shops) {
        this.shops = shops;
        return this;
    }

    public Gloves addShop(Shop shop) {
        this.shops.add(shop);
        shop.getGloves().add(this);
        return this;
    }

    public Gloves removeShop(Shop shop) {
        this.shops.remove(shop);
        shop.getGloves().remove(this);
        return this;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
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
        Gloves gloves = (Gloves) o;
        if (gloves.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gloves.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gloves{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            ", defence=" + getDefence() +
            ", part='" + getPart() + "'" +
            "}";
    }
}
