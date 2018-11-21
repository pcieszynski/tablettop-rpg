package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Helmet entity.
 */
public class HelmetDTO implements Serializable {

    private Long id;

    private String name;

    private String effect;

    private Integer price;

    private Integer defence;

    private String part;

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

    public Integer getDefence() {
        return defence;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
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

        HelmetDTO helmetDTO = (HelmetDTO) o;
        if (helmetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), helmetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HelmetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effect='" + getEffect() + "'" +
            ", price=" + getPrice() +
            ", defence=" + getDefence() +
            ", part='" + getPart() + "'" +
            "}";
    }
}
