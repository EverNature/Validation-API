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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Prediction {

    @Id
    @Column(name = "prediction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "registro_id", referencedColumnName = "registro_id")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "animal_detectado", referencedColumnName = "animal_id")
    private Animal detectedAnimal;

    @ManyToOne
    @JoinColumn(name="animal_corregido", referencedColumnName = "animal_id")
    private Animal correctedAnimal;

    @ManyToOne
    @JoinColumn(name="experto_id")
    private Expert correctorExpert;

    @Column(name = "es_correcto")
    private Boolean isCorrect;

    @Column(name = "confianza")
    private Float confidence;

    @Column(name = "mensaje")
    private String message;

    @Column(name = "direccon_imagen")
    private String predictedImagePath;

    @Column(name = "es_predecido")
    private Boolean isPredicted;

    @CreationTimestamp
    @Column(name = "fecha_prediccion", updatable = false)
    private Timestamp predictionDate;
}
