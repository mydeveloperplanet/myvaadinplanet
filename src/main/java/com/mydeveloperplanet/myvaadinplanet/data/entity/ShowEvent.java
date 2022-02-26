package com.mydeveloperplanet.myvaadinplanet.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mydeveloperplanet.myvaadinplanet.data.AbstractEntity;

@Entity
public class ShowEvent extends AbstractEntity {

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @NotNull
    @JsonIgnoreProperties({"shows"})
    private Show show;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
