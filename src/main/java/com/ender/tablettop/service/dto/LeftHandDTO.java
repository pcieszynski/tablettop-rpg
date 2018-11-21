package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LeftHand entity.
 */
public class LeftHandDTO implements Serializable {

    private Long id;

    private String name;

    private String effect;

    private Integer price;

    private String attack;

    private Integer defense;

    private String type;

    private Set<ShopDTO> shops = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ShopDTO> getShops() {
        return shops;
    }

    public void setShops(Set<ShopDTO> shops) {
        this.shops = shops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeftHandDTO leftHandDTO = (LeftHandDTO) o;
        if (leftHandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leftHandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeftHandDTO{" +
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
