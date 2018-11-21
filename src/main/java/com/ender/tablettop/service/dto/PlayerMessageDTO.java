package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the PlayerMessage entity.
 */
public class PlayerMessageDTO implements Serializable {

    private Long id;

    @Lob
    private String message;

    private String attack;

    private Integer heal;

    private Integer difficulty;

    private Boolean success;

    private String attribute;

    private Long playerId;

    private Long eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getHeal() {
        return heal;
    }

    public void setHeal(Integer heal) {
        this.heal = heal;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerMessageDTO playerMessageDTO = (PlayerMessageDTO) o;
        if (playerMessageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerMessageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerMessageDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", attack='" + getAttack() + "'" +
            ", heal=" + getHeal() +
            ", difficulty=" + getDifficulty() +
            ", success='" + isSuccess() + "'" +
            ", attribute='" + getAttribute() + "'" +
            ", player=" + getPlayerId() +
            ", event=" + getEventId() +
            "}";
    }
}
