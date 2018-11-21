package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Shop entity.
 */
public class ShopDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean isLoot;

    private Set<ItemDTO> items = new HashSet<>();

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

    public Boolean isIsLoot() {
        return isLoot;
    }

    public void setIsLoot(Boolean isLoot) {
        this.isLoot = isLoot;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemDTO> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShopDTO shopDTO = (ShopDTO) o;
        if (shopDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShopDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isLoot='" + isIsLoot() + "'" +
            "}";
    }
}
