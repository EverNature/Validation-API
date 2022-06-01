package eus.evernature.evern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.json_responses.AnimalPrediction;
import eus.evernature.evern.models.json_responses.DetectedVsInvasorAnimals;
import eus.evernature.evern.models.json_responses.PredictionTypes;
import eus.evernature.evern.service.prediction.PredictionService;

@RestController
@RequestMapping("/api/prediction")
public class PredictionController {

    @Autowired
    PredictionService predictionService;

    @GetMapping
    public ResponseEntity<List<Prediction>> getPredictions() {
        return ResponseEntity.ok().body(predictionService.getPredictions());
    }

    @GetMapping("/detected-species")
    public ResponseEntity<DetectedVsInvasorAnimals> getDetectedAndInvasorSpecies() {

        DetectedVsInvasorAnimals detectedAnimals = predictionService.getDetectedVsInvasorAnimals();

        return ResponseEntity.ok().body(detectedAnimals);
    }

    @GetMapping("/prediction-types")
    public ResponseEntity<PredictionTypes> getPredictionTypes() {

        PredictionTypes predictionTypes = predictionService.getPredictionTypes();

        return ResponseEntity.ok().body(predictionTypes);
    }

    @GetMapping("/animal-predicted")
    public ResponseEntity<List<AnimalPrediction>> getPredictedAnimals() {

        List<AnimalPrediction> animalPrediction = predictionService.getNumPredPerAnimal();

        return ResponseEntity.ok().body(animalPrediction);
    }

}
