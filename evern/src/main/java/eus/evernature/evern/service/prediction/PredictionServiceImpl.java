package eus.evernature.evern.service.prediction;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.JsonResponses.DetectedVsInvasorAnimals;
import eus.evernature.evern.repository.PredictionRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    PredictionRepository predictionRepository;

    @Override
    public Prediction savePrediction(Prediction prediction) {
        return predictionRepository.save(prediction);
    }

    @Override
    public Prediction getPrediction(Integer predictionId) {
        return predictionRepository.getById(predictionId);
    }

    @Override
    public List<Prediction> getPredictions() {
        List<Prediction> predictions = predictionRepository.findAll();
        return predictions == null ? new ArrayList<>() : predictions;
    }

    @Override
    public Prediction updatePrediction(Integer predictionId, Prediction prediction) {
        Prediction pred = predictionRepository.getById(predictionId);
        pred = prediction;
        return predictionRepository.save(pred);
    }

    @Override
    public DetectedVsInvasorAnimals getDetectedVsInvasorAnimals() {
        List<Prediction> predictions = predictionRepository.findAll();
        // predictions = predictions.equals(null) ? new ArrayList<>() : predictions;

        DetectedVsInvasorAnimals detectedVsInvasorAnimals = new DetectedVsInvasorAnimals();
        Integer detectedAnimals = predictions.size();
        Long invasorAnimals = predictions.stream().filter(p -> p.getDetectedAnimal().isInvasor() == true).count();

        detectedVsInvasorAnimals.setDetectados(detectedAnimals);
        detectedVsInvasorAnimals.setInvasores(invasorAnimals.intValue());

        return detectedVsInvasorAnimals;
    }
}
