package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gamemaster.
 */
@Entity
@Table(name = "gamemaster")
public class Gamemaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "gamemaster")
    private Set<Game> games = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("gamemasters")
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Gamemaster games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public Gamemaster addGame(Game game) {
        this.games.add(game);
        game.setGamemaster(this);
        return this;
    }

    public Gamemaster removeGame(Game game) {
        this.games.remove(game);
        game.setGamemaster(null);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Player getPlayer() {
        return player;
    }

    public Gamemaster player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        Gamemaster gamemaster = (Gamemaster) o;
        if (gamemaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gamemaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gamemaster{" +
            "id=" + getId() +
            "}";
    }
}
