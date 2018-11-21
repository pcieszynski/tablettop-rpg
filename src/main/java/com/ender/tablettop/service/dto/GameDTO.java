package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

    private Long id;

    private Long currentEvent;

    private String currentPlayer;

    private Set<PlayerDTO> players = new HashSet<>();

    private Long gamemasterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Long currentEvent) {
        this.currentEvent = currentEvent;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }

    public Long getGamemasterId() {
        return gamemasterId;
    }

    public void setGamemasterId(Long gamemasterId) {
        this.gamemasterId = gamemasterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;
        if (gameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + getId() +
            ", currentEvent=" + getCurrentEvent() +
            ", currentPlayer='" + getCurrentPlayer() + "'" +
            ", gamemaster=" + getGamemasterId() +
            "}";
    }
}
