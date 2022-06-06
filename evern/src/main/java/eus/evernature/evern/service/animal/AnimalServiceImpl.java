package eus.evernature.evern.service.animal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.repository.AnimalRepository;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Optional<Animal> getAnimalByName(String animalName) {
        return animalRepository.findByName(animalName);
    }

    
}
