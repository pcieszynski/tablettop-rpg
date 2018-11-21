package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "event")
    private Set<Npc> npcs = new HashSet<>();
    @OneToMany(mappedBy = "event")
    private Set<Battle> battles = new HashSet<>();
    @OneToMany(mappedBy = "event")
    private Set<PlayerMessage> playerMessages = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("events")
    private Game game;

    @ManyToOne
    @JsonIgnoreProperties("events")
    private Shop shop;

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

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Npc> getNpcs() {
        return npcs;
    }

    public Event npcs(Set<Npc> npcs) {
        this.npcs = npcs;
        return this;
    }

    public Event addNpc(Npc npc) {
        this.npcs.add(npc);
        npc.setEvent(this);
        return this;
    }

    public Event removeNpc(Npc npc) {
        this.npcs.remove(npc);
        npc.setEvent(null);
        return this;
    }

    public void setNpcs(Set<Npc> npcs) {
        this.npcs = npcs;
    }

    public Set<Battle> getBattles() {
        return battles;
    }

    public Event battles(Set<Battle> battles) {
        this.battles = battles;
        return this;
    }

    public Event addBattle(Battle battle) {
        this.battles.add(battle);
        battle.setEvent(this);
        return this;
    }

    public Event removeBattle(Battle battle) {
        this.battles.remove(battle);
        battle.setEvent(null);
        return this;
    }

    public void setBattles(Set<Battle> battles) {
        this.battles = battles;
    }

    public Set<PlayerMessage> getPlayerMessages() {
        return playerMessages;
    }

    public Event playerMessages(Set<PlayerMessage> playerMessages) {
        this.playerMessages = playerMessages;
        return this;
    }

    public Event addPlayerMessage(PlayerMessage playerMessage) {
        this.playerMessages.add(playerMessage);
        playerMessage.setEvent(this);
        return this;
    }

    public Event removePlayerMessage(PlayerMessage playerMessage) {
        this.playerMessages.remove(playerMessage);
        playerMessage.setEvent(null);
        return this;
    }

    public void setPlayerMessages(Set<PlayerMessage> playerMessages) {
        this.playerMessages = playerMessages;
    }

    public Game getGame() {
        return game;
    }

    public Event game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Shop getShop() {
        return shop;
    }

    public Event shop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
