package eus.evernature.evern.models.DTO;

import lombok.Data;

@Data
public class PredictionDTO {

    private String detectedAnimal;

    String image;

    private Float confidence;

    private String message;

    private Boolean isPredicted;
}
