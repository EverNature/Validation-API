package eus.evernature.evern.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "registro")
public class Record {
    
    @Id
    @Column(name = "registro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    List<Prediction> predictions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="camara_id", updatable = false)
    private Camera camera;

    @ManyToOne
    @JoinColumn(name="experto_id")
    private Expert correctorExpert;

    @Column(name = "es_correcto")
    private boolean isCorrect;
    
    @Column(name = "imp_path", updatable = false)
    private String imgPath;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private Timestamp recordDate;

    @UpdateTimestamp
    @Column(name = "fecha_validacion")
    private Timestamp validationDate;
}
