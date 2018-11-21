package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "current_event")
    private Long currentEvent;

    @Column(name = "current_player")
    private String currentPlayer;

    @OneToMany(mappedBy = "game")
    private Set<Event> events = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "game_player",
               joinColumns = @JoinColumn(name = "games_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> players = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("games")
    private Gamemaster gamemaster;

    @ManyToMany(mappedBy = "games")
    @JsonIgnore
    private Set<Character> characters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentEvent() {
        return currentEvent;
    }

    public Game currentEvent(Long currentEvent) {
        this.currentEvent = currentEvent;
        return this;
    }

    public void setCurrentEvent(Long currentEvent) {
        this.currentEvent = currentEvent;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public Game currentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Game events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Game addEvent(Event event) {
        this.events.add(event);
        event.setGame(this);
        return this;
    }

    public Game removeEvent(Event event) {
        this.events.remove(event);
        event.setGame(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Game players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Game addPlayer(Player player) {
        this.players.add(player);
        player.getGames().add(this);
        return this;
    }

    public Game removePlayer(Player player) {
        this.players.remove(player);
        player.getGames().remove(this);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Gamemaster getGamemaster() {
        return gamemaster;
    }

    public Game gamemaster(Gamemaster gamemaster) {
        this.gamemaster = gamemaster;
        return this;
    }

    public void setGamemaster(Gamemaster gamemaster) {
        this.gamemaster = gamemaster;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Game characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Game addCharacter(Character character) {
        this.characters.add(character);
        character.getGames().add(this);
        return this;
    }

    public Game removeCharacter(Character character) {
        this.characters.remove(character);
        character.getGames().remove(this);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
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
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", currentEvent=" + getCurrentEvent() +
            ", currentPlayer='" + getCurrentPlayer() + "'" +
            "}";
    }
}
