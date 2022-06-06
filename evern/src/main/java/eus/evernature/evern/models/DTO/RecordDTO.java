package eus.evernature.evern.models.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RecordDTO {
    List<PredictionDTO> predictions = new ArrayList<>();

    String image;
    
    private Integer cameraId;

}
