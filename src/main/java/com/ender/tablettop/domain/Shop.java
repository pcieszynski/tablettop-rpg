package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shop.
 */
@Entity
@Table(name = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_loot")
    private Boolean isLoot;

    @OneToMany(mappedBy = "shop")
    private Set<Event> events = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "shop_item",
               joinColumns = @JoinColumn(name = "shops_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "items_id", referencedColumnName = "id"))
    private Set<Item> items = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<Helmet> helmets = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<Armour> armours = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<Boots> boots = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<Gloves> gloves = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<Legs> legs = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<RightHand> rightHands = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<LeftHand> leftHands = new HashSet<>();

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

    public Shop name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsLoot() {
        return isLoot;
    }

    public Shop isLoot(Boolean isLoot) {
        this.isLoot = isLoot;
        return this;
    }

    public void setIsLoot(Boolean isLoot) {
        this.isLoot = isLoot;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Shop events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Shop addEvent(Event event) {
        this.events.add(event);
        event.setShop(this);
        return this;
    }

    public Shop removeEvent(Event event) {
        this.events.remove(event);
        event.setShop(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Shop items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Shop addItem(Item item) {
        this.items.add(item);
        item.getShops().add(this);
        return this;
    }

    public Shop removeItem(Item item) {
        this.items.remove(item);
        item.getShops().remove(this);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Helmet> getHelmets() {
        return helmets;
    }

    public Shop helmets(Set<Helmet> helmets) {
        this.helmets = helmets;
        return this;
    }

    public Shop addHelmet(Helmet helmet) {
        this.helmets.add(helmet);
        helmet.getShops().add(this);
        return this;
    }

    public Shop removeHelmet(Helmet helmet) {
        this.helmets.remove(helmet);
        helmet.getShops().remove(this);
        return this;
    }

    public void setHelmets(Set<Helmet> helmets) {
        this.helmets = helmets;
    }

    public Set<Armour> getArmours() {
        return armours;
    }

    public Shop armours(Set<Armour> armours) {
        this.armours = armours;
        return this;
    }

    public Shop addArmour(Armour armour) {
        this.armours.add(armour);
        armour.getShops().add(this);
        return this;
    }

    public Shop removeArmour(Armour armour) {
        this.armours.remove(armour);
        armour.getShops().remove(this);
        return this;
    }

    public void setArmours(Set<Armour> armours) {
        this.armours = armours;
    }

    public Set<Boots> getBoots() {
        return boots;
    }

    public Shop boots(Set<Boots> boots) {
        this.boots = boots;
        return this;
    }

    public Shop addBoots(Boots boots) {
        this.boots.add(boots);
        boots.getShops().add(this);
        return this;
    }

    public Shop removeBoots(Boots boots) {
        this.boots.remove(boots);
        boots.getShops().remove(this);
        return this;
    }

    public void setBoots(Set<Boots> boots) {
        this.boots = boots;
    }

    public Set<Gloves> getGloves() {
        return gloves;
    }

    public Shop gloves(Set<Gloves> gloves) {
        this.gloves = gloves;
        return this;
    }

    public Shop addGloves(Gloves gloves) {
        this.gloves.add(gloves);
        gloves.getShops().add(this);
        return this;
    }

    public Shop removeGloves(Gloves gloves) {
        this.gloves.remove(gloves);
        gloves.getShops().remove(this);
        return this;
    }

    public void setGloves(Set<Gloves> gloves) {
        this.gloves = gloves;
    }

    public Set<Legs> getLegs() {
        return legs;
    }

    public Shop legs(Set<Legs> legs) {
        this.legs = legs;
        return this;
    }

    public Shop addLegs(Legs legs) {
        this.legs.add(legs);
        legs.getShops().add(this);
        return this;
    }

    public Shop removeLegs(Legs legs) {
        this.legs.remove(legs);
        legs.getShops().remove(this);
        return this;
    }

    public void setLegs(Set<Legs> legs) {
        this.legs = legs;
    }

    public Set<RightHand> getRightHands() {
        return rightHands;
    }

    public Shop rightHands(Set<RightHand> rightHands) {
        this.rightHands = rightHands;
        return this;
    }

    public Shop addRightHand(RightHand rightHand) {
        this.rightHands.add(rightHand);
        rightHand.getShops().add(this);
        return this;
    }

    public Shop removeRightHand(RightHand rightHand) {
        this.rightHands.remove(rightHand);
        rightHand.getShops().remove(this);
        return this;
    }

    public void setRightHands(Set<RightHand> rightHands) {
        this.rightHands = rightHands;
    }

    public Set<LeftHand> getLeftHands() {
        return leftHands;
    }

    public Shop leftHands(Set<LeftHand> leftHands) {
        this.leftHands = leftHands;
        return this;
    }

    public Shop addLeftHand(LeftHand leftHand) {
        this.leftHands.add(leftHand);
        leftHand.getShops().add(this);
        return this;
    }

    public Shop removeLeftHand(LeftHand leftHand) {
        this.leftHands.remove(leftHand);
        leftHand.getShops().remove(this);
        return this;
    }

    public void setLeftHands(Set<LeftHand> leftHands) {
        this.leftHands = leftHands;
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
        Shop shop = (Shop) o;
        if (shop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shop{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isLoot='" + isIsLoot() + "'" +
            "}";
    }
}
