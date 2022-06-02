package com.baranskagata.optometry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="work")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Basic
    private double price;

    //in minutes
    @Basic
    private int duration;


    @ManyToMany
    @JoinTable(name = "optometrist_work", joinColumns = @JoinColumn(name = "id_work"), inverseJoinColumns = @JoinColumn(name = "id_optometrist"))
    private List<Optometrist> optometrists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Work)) return false;
        Work work = (Work) o;
        return id.equals(work.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
