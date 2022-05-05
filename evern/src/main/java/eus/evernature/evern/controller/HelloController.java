package eus.evernature.evern.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    public String getHello(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello " + name + " rest";
    }
    
}
