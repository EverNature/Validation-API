package eus.evernature.evern.service.animal;

import java.util.Optional;

import eus.evernature.evern.models.Animal;

public interface AnimalService {
    Optional<Animal> getAnimalByName(String animalName);
}
