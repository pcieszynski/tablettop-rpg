package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PlayerMessage.
 */
@Entity
@Table(name = "player_message")
public class PlayerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "attack")
    private String attack;

    @Column(name = "heal")
    private Integer heal;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "attribute")
    private String attribute;

    @ManyToOne
    @JsonIgnoreProperties("playerMessages")
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties("playerMessages")
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public PlayerMessage message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttack() {
        return attack;
    }

    public PlayerMessage attack(String attack) {
        this.attack = attack;
        return this;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getHeal() {
        return heal;
    }

    public PlayerMessage heal(Integer heal) {
        this.heal = heal;
        return this;
    }

    public void setHeal(Integer heal) {
        this.heal = heal;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public PlayerMessage difficulty(Integer difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean isSuccess() {
        return success;
    }

    public PlayerMessage success(Boolean success) {
        this.success = success;
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAttribute() {
        return attribute;
    }

    public PlayerMessage attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerMessage player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Event getEvent() {
        return event;
    }

    public PlayerMessage event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        PlayerMessage playerMessage = (PlayerMessage) o;
        if (playerMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerMessage{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", attack='" + getAttack() + "'" +
            ", heal=" + getHeal() +
            ", difficulty=" + getDifficulty() +
            ", success='" + isSuccess() + "'" +
            ", attribute='" + getAttribute() + "'" +
            "}";
    }
}
