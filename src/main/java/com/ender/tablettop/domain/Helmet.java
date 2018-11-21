package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Helmet.
 */
@Entity
@Table(name = "helmet")
public class Helmet implements Serializable {

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

    @OneToMany(mappedBy = "helmet")
    private Set<Character> characters = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "helmet_shop",
               joinColumns = @JoinColumn(name = "helmets_id", referencedColumnName = "id"),
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

    public Helmet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public Helmet effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public Helmet price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDefence() {
        return defence;
    }

    public Helmet defence(Integer defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public String getPart() {
        return part;
    }

    public Helmet part(String part) {
        this.part = part;
        return this;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Helmet characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Helmet addCharacter(Character character) {
        this.characters.add(character);
        character.setHelmet(this);
        return this;
    }

    public Helmet removeCharacter(Character character) {
        this.characters.remove(character);
        character.setHelmet(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public Helmet shops(Set<Shop> shops) {
        this.shops = shops;
        return this;
    }

    public Helmet addShop(Shop shop) {
        this.shops.add(shop);
        shop.getHelmets().add(this);
        return this;
    }

    public Helmet removeShop(Shop shop) {
        this.shops.remove(shop);
        shop.getHelmets().remove(this);
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
        Helmet helmet = (Helmet) o;
        if (helmet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), helmet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Helmet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            ", defence=" + getDefence() +
            ", part='" + getPart() + "'" +
            "}";
    }
}
