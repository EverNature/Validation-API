package eus.evernature.evern.service.animal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.json_responses.AnimalIsInvasor;
import eus.evernature.evern.repository.AnimalRepository;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Optional<Animal> getAnimalByName(String animalName) {
        return animalRepository.findByName(animalName);
    }

    @Override
    public AnimalIsInvasor isAnimalInvasor(String animalName) {
        AnimalIsInvasor animalIsInvasor = new Gson().fromJson(animalName, AnimalIsInvasor.class);

        Optional<Animal> foundAnimal = getAnimalByName(animalIsInvasor.getAnimalName()); 

        animalIsInvasor.setInvasor(foundAnimal.isPresent() ? foundAnimal.get().isInvasor() : false);

        return animalIsInvasor;
    }    
}
