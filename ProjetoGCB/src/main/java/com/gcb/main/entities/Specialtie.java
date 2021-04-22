package com.gcb.main.entities;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="tb_specialties")
public class Specialtie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean deleted;

    public Specialtie(Long id, String name, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
    }

    @ManyToMany(mappedBy = "specialties", targetEntity = Doctor.class)
    private Set<Doctor> doctors = new HashSet<>();

    public Specialtie(){

    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialtie that = (Specialtie) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}