package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.evernature.evern.models.json_responses.AnimalIsInvasor;
import eus.evernature.evern.service.animal.AnimalService;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {
    
    @Autowired
    AnimalService animalService;


    @GetMapping("/invasorList")
    public ResponseEntity<AnimalIsInvasor> getInvasorAnimalsLiEntity(@RequestBody String animalNameJson) {

        AnimalIsInvasor detectedAnimals = animalService.isAnimalInvasor(animalNameJson);

        return ResponseEntity.ok().body(detectedAnimals);
    }

}
