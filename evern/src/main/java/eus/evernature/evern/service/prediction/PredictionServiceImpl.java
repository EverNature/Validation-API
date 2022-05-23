package eus.evernature.evern.service.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.JsonResponses.AnimalPrediction;
import eus.evernature.evern.models.JsonResponses.DetectedVsInvasorAnimals;
import eus.evernature.evern.models.JsonResponses.PredictionTypes;
import eus.evernature.evern.repository.PredictionRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
/**
 * > This class implements the PredictionService interface
 */
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
        predictions = predictions.equals(null) ? new ArrayList<>() : predictions;

        DetectedVsInvasorAnimals detectedVsInvasorAnimals = new DetectedVsInvasorAnimals();
        Integer detectedAnimals = predictions.size();
        Long invasorAnimals = predictions.stream().filter(p -> p.getDetectedAnimal().isInvasor() == true).count();

        detectedVsInvasorAnimals.setDetectados(detectedAnimals);
        detectedVsInvasorAnimals.setInvasores(invasorAnimals.intValue());

        return detectedVsInvasorAnimals;
    }

    @Override
    public PredictionTypes getPredictionTypes() {
        List<Prediction> predictions = predictionRepository.findAll();
        predictions = predictions.equals(null) ? new ArrayList<>() : predictions;

        PredictionTypes predictionTypes = new PredictionTypes();
        
        Long validationPendant = predictions.stream().filter(p -> checkValidationPendant(p)).count();
        
        // Quitar de la lista las validation pendant o pasar la lista a las funciones como sobrecarga y mirar si no estan incluidas en la lista de pendant
        // Creo que va a ser mejor la segunda opcion poruq la primera huele un poco a culo
        Long validas = predictions.stream().filter(p -> checkPredictionIsCorrect(p)).count();

        Long falsePositives = predictions.stream().filter(p -> checkFalsePositive(p)).count();

        predictionTypes.setFalsosPositivos(falsePositives.intValue());
        predictionTypes.setPrediccionesValidadas(validas.intValue());
        predictionTypes.setPendientesDeValidacion(validationPendant.intValue());

        return predictionTypes;    
    }

    // TODO: si isCorrect es null quiere decir que todavia no lo han validado. Por lo cual o metes una respuesta custom para esto o miras
    // si tiene experto corrector y luego el boolean.
    // Conclusion: si no está corregida no la quieres contar. if(checkValidationPendant la sacas de la lista maybe)?
    private boolean checkPredictionIsCorrect(Prediction pred) {
        Optional<Boolean> optIsCorrect = Optional.ofNullable(pred.getIsCorrect());

        return optIsCorrect.isPresent() && optIsCorrect.get() ? true : false;
    }

    private boolean checkFalsePositive(Prediction pred) {
        Optional<Animal> optCorrectedAnimal = Optional.ofNullable(pred.getCorrectedAnimal());

        if(pred.getDetectedAnimal().isInvasor() && optCorrectedAnimal.isPresent() && !optCorrectedAnimal.get().isInvasor())
            return false;

        return true;
    }

    private boolean checkValidationPendant(Prediction pred) {
        Optional<Expert> optExpert = Optional.ofNullable(pred.getCorrectorExpert());

        return optExpert.isPresent() ? false : true;
    }

    @Override
    public List<AnimalPrediction> getNumPredPerAnimal() {
        List<AnimalPrediction> animalPredictions = new ArrayList<>();
        List<Prediction> predictions = predictionRepository.findAll();
        Map<String, Integer> animalTimesMap = new HashMap<>();

        for(Prediction p : predictions) {
            String animalName = p.getDetectedAnimal().getName();
            
            if(animalTimesMap.get(animalName) == null) {
                animalTimesMap.put(animalName, 1);
            } else {
                int value = animalTimesMap.get(animalName);
                animalTimesMap.put(animalName, ++value );
            }
        }

        for(Entry<String, Integer> entry : animalTimesMap.entrySet()) {
            AnimalPrediction ap = new AnimalPrediction();
            ap.setEspecie(entry.getKey());
            ap.setNumImagenes(entry.getValue());

            animalPredictions.add(ap);
        }

        return animalPredictions;
    }
}
