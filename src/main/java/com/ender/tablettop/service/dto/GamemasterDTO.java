package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Gamemaster entity.
 */
public class GamemasterDTO implements Serializable {

    private Long id;

    private PlayerDTO playerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerDTO getPlayerId() {
        return playerId;
    }

    public void setPlayerId(PlayerDTO playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GamemasterDTO gamemasterDTO = (GamemasterDTO) o;
        if (gamemasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gamemasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GamemasterDTO{" +
            "id=" + getId() +
            ", player=" + getPlayerId() +
            "}";
    }
}
