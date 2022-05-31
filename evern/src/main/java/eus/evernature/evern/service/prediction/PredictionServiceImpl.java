package eus.evernature.evern.service.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.JsonResponses.AnimalPrediction;
import eus.evernature.evern.models.JsonResponses.DetectedVsInvasorAnimals;
import eus.evernature.evern.models.JsonResponses.PredictionTypes;
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
        return predictionRepository.findAll();
    }

    @Override
    public Prediction updatePrediction(Integer predictionId, Prediction prediction) {
        prediction.setId(predictionId);

        return predictionRepository.existsById(predictionId) ? predictionRepository.save(prediction) : null;
    }

    @Override
    public DetectedVsInvasorAnimals getDetectedVsInvasorAnimals() {

        DetectedVsInvasorAnimals detectedVsInvasorAnimals = new DetectedVsInvasorAnimals();
        Long detectedAnimals = predictionRepository.count();
        Long invasorAnimals = getInvasorAnimalsPredictionCount();

        detectedVsInvasorAnimals.setDetectados(detectedAnimals.intValue());
        detectedVsInvasorAnimals.setInvasores(invasorAnimals.intValue());

        return detectedVsInvasorAnimals;
    }

    @Override
    public PredictionTypes getPredictionTypes() {
        PredictionTypes predictionTypes = new PredictionTypes();

        predictionTypes.setPendientesDeValidacion(getPendantPredictionsCount().intValue());
        predictionTypes.setPrediccionesValidadas(getCorrectPredictionsCount().intValue());
        predictionTypes.setFalsosPositivos(getFalsePositivePredictionsCount().intValue());

        return predictionTypes;    
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

    private Long getPendantPredictionsCount() {
        Specification<Prediction> spec = Specification.where((root, query, cb) -> cb.isNull(root.get("correctorExpert")));
        return predictionRepository.count(spec);
    }

    private Long getCorrectPredictionsCount() {
        Specification<Prediction> spec = Specification.where((root, query, cb) -> cb.isNotNull(root.get("correctorExpert")));
        Specification<Prediction> specIsCorrect = Specification.where((root, query, cb) -> cb.isTrue(root.get("isCorrect")));


        Specification<Prediction> findSpec = Specification.where(spec).and(specIsCorrect);
        
        return predictionRepository.count(findSpec);
    }

    private Long getFalsePositivePredictionsCount() {
        Specification<Prediction> specIsInvasor = Specification.where((root, query, cb) -> cb.isTrue(root.get("detectedAnimal").get("isInvasor")));
        Specification<Prediction> specIsCorrected = Specification.where((root, query, cb) -> cb.isNotNull(root.get("correctedAnimal")));
        Specification<Prediction> specIsNotInvasor = Specification.where((root, query, cb) -> cb.isFalse(root.get("correctedAnimal").get("isInvasor")));


        Specification<Prediction> findSpec = Specification.where(specIsInvasor).and(specIsCorrected).and(specIsNotInvasor);
        
        return predictionRepository.count(findSpec);
    }

    private Long getInvasorAnimalsPredictionCount() {
        Specification<Prediction> specIsInvasor = Specification.where((root, query, cb) -> cb.isTrue(root.get("detectedAnimal").get("isInvasor")));

        Specification<Prediction> findSpec = Specification.where(specIsInvasor);
        
        return predictionRepository.count(findSpec);
    }
} 
