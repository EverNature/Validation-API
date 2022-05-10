package eus.evernature.evern.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Column(name = "confianza")
    private Float confidence;

    @Column(name = "mensaje")
    private String message;

    @Column(name = "direccon_imagen")
    private String predictedImagePath;

    @Column(name = "es_predecido")
    private Boolean isPredicted;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Prediction other = (Prediction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
