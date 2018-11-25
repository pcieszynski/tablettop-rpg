package com.ender.tablettop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "character")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_level")
    private Integer level;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "max_hitpoints")
    private Integer maxHitpoints;

    @Column(name = "current_hitpoints")
    private Integer currentHitpoints;

    @Column(name = "gold")
    private Integer gold;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "agility")
    private Integer agility;

    @Column(name = "constituition")
    private Integer constituition;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "willpower")
    private Integer willpower;

    @Column(name = "charisma")
    private Integer charisma;

    @ManyToMany
    @JoinTable(name = "character_skill",
               joinColumns = @JoinColumn(name = "characters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "skills_id", referencedColumnName = "id"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "character_game",
               joinColumns = @JoinColumn(name = "characters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "games_id", referencedColumnName = "id"))
    private Set<Game> games = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "character_status",
               joinColumns = @JoinColumn(name = "characters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "statuses_id", referencedColumnName = "id"))
    private Set<Status> statuses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "character_item",
               joinColumns = @JoinColumn(name = "characters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "items_id", referencedColumnName = "id"))
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Profession profession;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Helmet helmet;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Armour armour;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Boots boots;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Gloves gloves;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private Legs legs;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private RightHand rightHand;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private LeftHand leftHand;

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

    public Character name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public Character level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public Character experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getMaxHitpoints() {
        return maxHitpoints;
    }

    public Character maxHitpoints(Integer maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
        return this;
    }

    public void setMaxHitpoints(Integer maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public Integer getCurrentHitpoints() {
        return currentHitpoints;
    }

    public Character currentHitpoints(Integer currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
        return this;
    }

    public void setCurrentHitpoints(Integer currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public Integer getGold() {
        return gold;
    }

    public Character gold(Integer gold) {
        this.gold = gold;
        return this;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getStrength() {
        return strength;
    }

    public Character strength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public Character agility(Integer agility) {
        this.agility = agility;
        return this;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getConstituition() {
        return constituition;
    }

    public Character constituition(Integer constituition) {
        this.constituition = constituition;
        return this;
    }

    public void setConstituition(Integer constituition) {
        this.constituition = constituition;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Character intelligence(Integer intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getWillpower() {
        return willpower;
    }

    public Character willpower(Integer willpower) {
        this.willpower = willpower;
        return this;
    }

    public void setWillpower(Integer willpower) {
        this.willpower = willpower;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public Character charisma(Integer charisma) {
        this.charisma = charisma;
        return this;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Character skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public Character addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getCharacters().add(this);
        return this;
    }

    public Character removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getCharacters().remove(this);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Character games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public Character addGame(Game game) {
        this.games.add(game);
        game.getCharacters().add(this);
        return this;
    }

    public Character removeGame(Game game) {
        this.games.remove(game);
        game.getCharacters().remove(this);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Character statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Character addStatus(Status status) {
        this.statuses.add(status);
        status.getCharacters().add(this);
        return this;
    }

    public Character removeStatus(Status status) {
        this.statuses.remove(status);
        status.getCharacters().remove(this);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Character items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Character addItem(Item item) {
        this.items.add(item);
        item.getCharacters().add(this);
        return this;
    }

    public Character removeItem(Item item) {
        this.items.remove(item);
        item.getCharacters().remove(this);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Profession getProfession() {
        return profession;
    }

    public Character profession(Profession profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Player getPlayer() {
        return player;
    }

    public Character player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public Character helmet(Helmet helmet) {
        this.helmet = helmet;
        return this;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public Armour getArmour() {
        return armour;
    }

    public Character armour(Armour armour) {
        this.armour = armour;
        return this;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public Boots getBoots() {
        return boots;
    }

    public Character boots(Boots boots) {
        this.boots = boots;
        return this;
    }

    public void setBoots(Boots boots) {
        this.boots = boots;
    }

    public Gloves getGloves() {
        return gloves;
    }

    public Character gloves(Gloves gloves) {
        this.gloves = gloves;
        return this;
    }

    public void setGloves(Gloves gloves) {
        this.gloves = gloves;
    }

    public Legs getLegs() {
        return legs;
    }

    public Character legs(Legs legs) {
        this.legs = legs;
        return this;
    }

    public void setLegs(Legs legs) {
        this.legs = legs;
    }

    public RightHand getRightHand() {
        return rightHand;
    }

    public Character rightHand(RightHand rightHand) {
        this.rightHand = rightHand;
        return this;
    }

    public void setRightHand(RightHand rightHand) {
        this.rightHand = rightHand;
    }

    public LeftHand getLeftHand() {
        return leftHand;
    }

    public Character leftHand(LeftHand leftHand) {
        this.leftHand = leftHand;
        return this;
    }

    public void setLeftHand(LeftHand leftHand) {
        this.leftHand = leftHand;
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
        Character character = (Character) o;
        if (character.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), character.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Character{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            ", experience=" + getExperience() +
            ", maxHitpoints=" + getMaxHitpoints() +
            ", currentHitpoints=" + getCurrentHitpoints() +
            ", gold=" + getGold() +
            ", strength=" + getStrength() +
            ", agility=" + getAgility() +
            ", constituition=" + getConstituition() +
            ", intelligence=" + getIntelligence() +
            ", willpower=" + getWillpower() +
            ", charisma=" + getCharisma() +
            "}";
    }
}
