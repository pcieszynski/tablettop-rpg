package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RightHand.
 */
@Entity
@Table(name = "right_hand")
public class RightHand implements Serializable {

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

    @Column(name = "attack")
    private String attack;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "jhi_type")
    private String type;

    @OneToMany(mappedBy = "rightHand")
    private Set<Character> characters = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "right_hand_shop",
               joinColumns = @JoinColumn(name = "right_hands_id", referencedColumnName = "id"),
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

    public RightHand name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public RightHand effect(String effect) {
        this.effect = effect;
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public RightHand price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAttack() {
        return attack;
    }

    public RightHand attack(String attack) {
        this.attack = attack;
        return this;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public RightHand defense(Integer defense) {
        this.defense = defense;
        return this;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public String getType() {
        return type;
    }

    public RightHand type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public RightHand characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public RightHand addCharacter(Character character) {
        this.characters.add(character);
        character.setRightHand(this);
        return this;
    }

    public RightHand removeCharacter(Character character) {
        this.characters.remove(character);
        character.setRightHand(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public RightHand shops(Set<Shop> shops) {
        this.shops = shops;
        return this;
    }

    public RightHand addShop(Shop shop) {
        this.shops.add(shop);
        shop.getRightHands().add(this);
        return this;
    }

    public RightHand removeShop(Shop shop) {
        this.shops.remove(shop);
        shop.getRightHands().remove(this);
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
        RightHand rightHand = (RightHand) o;
        if (rightHand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rightHand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RightHand{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            ", attack='" + getAttack() + "'" +
            ", defense=" + getDefense() +
            ", type='" + getType() + "'" +
            "}";
    }
}
