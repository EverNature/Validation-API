package eus.evernature.evern.service.animal;

import java.util.Optional;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.json_responses.AnimalIsInvasor;

public interface AnimalService {
    Optional<Animal> getAnimalByName(String animalName);
    AnimalIsInvasor isAnimalInvasor(String animalName);
}
