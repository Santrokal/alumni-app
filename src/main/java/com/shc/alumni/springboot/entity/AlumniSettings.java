package com.shc.alumni.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "alumni_settings")
public class AlumniSettings {
    @Id
    private Long id;
    private boolean agmFormActive;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isAgmFormActive() { return agmFormActive; }
    public void setAgmFormActive(boolean agmFormActive) { this.agmFormActive = agmFormActive; }
}