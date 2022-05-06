package eus.evernature.evern.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "registro")
public class Record {
    
    @Id
    @Column(name = "registro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="camara_id")
    private Camera camera;

    @ManyToOne
    @JoinColumn(name="animal_detectado", referencedColumnName = "animal_id")
    private Animal detectedAnimal;

    @ManyToOne
    @JoinColumn(name="experto_id")
    private Expert correctorExpert;

    @ManyToOne
    @JoinColumn(name="animal_corregido", referencedColumnName = "animal_id")
    private Animal correctedAnimal;

    @Column(name = "es_correcto")
    private boolean isCorrect;
    
    @Column(name = "imp_path")
    private String imgPath;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private Timestamp recordDate;
}
