package com.mydeveloperplanet.myvaadinplanet.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.mydeveloperplanet.myvaadinplanet.data.AbstractEntity;

@Entity
public class Show extends AbstractEntity {
    @NotBlank
    private String title;

    @OneToMany(mappedBy = "show")
    private List<ShowEvent> events = new LinkedList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ShowEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ShowEvent> events) {
        this.events = events;
    }
}
