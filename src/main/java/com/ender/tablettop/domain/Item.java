package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

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

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private Set<Shop> shops = new HashSet<>();

    @ManyToMany(mappedBy = "items")
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

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public Item effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public Item price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public Item shops(Set<Shop> shops) {
        this.shops = shops;
        return this;
    }

    public Item addShop(Shop shop) {
        this.shops.add(shop);
        shop.getItems().add(this);
        return this;
    }

    public Item removeShop(Shop shop) {
        this.shops.remove(shop);
        shop.getItems().remove(this);
        return this;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Item characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Item addCharacter(Character character) {
        this.characters.add(character);
        character.getItems().add(this);
        return this;
    }

    public Item removeCharacter(Character character) {
        this.characters.remove(character);
        character.getItems().remove(this);
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
        Item item = (Item) o;
        if (item.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
