package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.repository.ExpertRepository;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired 
    ExpertRepository expertRepository;

    // public RegisterController() {
    //     this.expertRepository = expertRepository;
    // }

    @GetMapping(path = "/register/test", produces = MediaType.APPLICATION_XML_VALUE)
    public Expert getTestExpert() {
        Expert expert = new Expert();

        expert.setName("DE PRUEBA");
        expert.setUsername("ELEPEPE");

        return expert;
    }

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Expert registeExpert(@RequestBody Expert expert) {
        expert = expertRepository.save(expert);

        return expert;
    }
    
}
