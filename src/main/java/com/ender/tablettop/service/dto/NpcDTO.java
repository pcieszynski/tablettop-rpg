package com.ender.tablettop.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Npc entity.
 */
public class NpcDTO implements Serializable {

    private Long id;

    private String name;

    private Long eventId;

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

        NpcDTO npcDTO = (NpcDTO) o;
        if (npcDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), npcDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NpcDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", event=" + getEventId() +
            "}";
    }
}
