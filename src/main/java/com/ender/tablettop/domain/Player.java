package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "keycloak_id")
    private String keycloakId;

    @OneToMany(mappedBy = "player")
    private Set<Gamemaster> gamemasters = new HashSet<>();
    @OneToMany(mappedBy = "player")
    private Set<Character> characters = new HashSet<>();
    @OneToMany(mappedBy = "player")
    private Set<PlayerMessage> playerMessages = new HashSet<>();
    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Player username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public Player keycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
        return this;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public Set<Gamemaster> getGamemasters() {
        return gamemasters;
    }

    public Player gamemasters(Set<Gamemaster> gamemasters) {
        this.gamemasters = gamemasters;
        return this;
    }

    public Player addGamemaster(Gamemaster gamemaster) {
        this.gamemasters.add(gamemaster);
        gamemaster.setPlayer(this);
        return this;
    }

    public Player removeGamemaster(Gamemaster gamemaster) {
        this.gamemasters.remove(gamemaster);
        gamemaster.setPlayer(null);
        return this;
    }

    public void setGamemasters(Set<Gamemaster> gamemasters) {
        this.gamemasters = gamemasters;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Player characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Player addCharacter(Character character) {
        this.characters.add(character);
        character.setPlayer(this);
        return this;
    }

    public Player removeCharacter(Character character) {
        this.characters.remove(character);
        character.setPlayer(null);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<PlayerMessage> getPlayerMessages() {
        return playerMessages;
    }

    public Player playerMessages(Set<PlayerMessage> playerMessages) {
        this.playerMessages = playerMessages;
        return this;
    }

    public Player addPlayerMessage(PlayerMessage playerMessage) {
        this.playerMessages.add(playerMessage);
        playerMessage.setPlayer(this);
        return this;
    }

    public Player removePlayerMessage(PlayerMessage playerMessage) {
        this.playerMessages.remove(playerMessage);
        playerMessage.setPlayer(null);
        return this;
    }

    public void setPlayerMessages(Set<PlayerMessage> playerMessages) {
        this.playerMessages = playerMessages;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Player games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public Player addGame(Game game) {
        this.games.add(game);
        game.getPlayers().add(this);
        return this;
    }

    public Player removeGame(Game game) {
        this.games.remove(game);
        game.getPlayers().remove(this);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
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
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", keycloakId='" + getKeycloakId() + "'" +
            "}";
    }
}
