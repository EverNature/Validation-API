package eus.evernature.evern.service.prediction;

import java.util.List;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.json_responses.AnimalPrediction;
import eus.evernature.evern.models.json_responses.DetectedVsInvasorAnimals;
import eus.evernature.evern.models.json_responses.PredictionTypes;

public interface PredictionService {
    Prediction savePrediction(Prediction prediction);
    Prediction getPrediction(Integer predictionId);
    List<Prediction> getPredictions();
    Prediction updatePrediction(Integer predictionId, Prediction prediction);
    DetectedVsInvasorAnimals getDetectedVsInvasorAnimals();
    PredictionTypes getPredictionTypes();
    List<AnimalPrediction> getNumPredPerAnimal();

}
