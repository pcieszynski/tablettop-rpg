package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Boots.
 */
@Entity
@Table(name = "boots")
public class Boots implements Serializable {

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

    @OneToMany(mappedBy = "boots")
    private Set<Character> characters = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "boots_shop",
               joinColumns = @JoinColumn(name = "boots_id", referencedColumnName = "id"),
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

    public Boots name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public Boots effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public Boots price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDefence() {
        return defence;
    }

    public Boots defence(Integer defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public String getPart() {
        return part;
    }

    public Boots part(String part) {
        this.part = part;
        return this;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Boots characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Boots addCharacter(Character character) {
        this.characters.add(character);
        character.setBoots(this);
        return this;
    }

    public Boots removeCharacter(Character character) {
        this.characters.remove(character);
        character.setBoots(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public Boots shops(Set<Shop> shops) {
        this.shops = shops;
        return this;
    }

    public Boots addShop(Shop shop) {
        this.shops.add(shop);
        shop.getBoots().add(this);
        return this;
    }

    public Boots removeShop(Shop shop) {
        this.shops.remove(shop);
        shop.getBoots().remove(this);
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
        Boots boots = (Boots) o;
        if (boots.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boots.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Boots{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            ", defence=" + getDefence() +
            ", part='" + getPart() + "'" +
            "}";
    }
}
