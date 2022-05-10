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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity(name = "animal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Animal {
    
    @Id
    @Column(name = "animal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean isInvasor;

    @OneToMany(mappedBy = "detectedAnimal", cascade = CascadeType.ALL)
    List<Prediction> detectedInRecords = new ArrayList<>();

    @OneToMany(mappedBy = "correctedAnimal", cascade = CascadeType.ALL)
    List<Prediction> correctedInRecords = new ArrayList<>();
   
}
