package eus.evernature.evern.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "animal")
public class Animal {
    
    @Id
    @Column(name = "animal_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private boolean isInvasor;

    @OneToMany(mappedBy = "detectedAnimal", cascade = CascadeType.ALL)
    List<Record> detectedInRecords = new ArrayList<>();

    @OneToMany(mappedBy = "correctedAnimal", cascade = CascadeType.ALL)
    List<Record> correctedInRecords = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInvasor() {
        return isInvasor;
    }

    public void setInvasor(boolean isInvasor) {
        this.isInvasor = isInvasor;
    }
    
}
