package eus.evernature.evern.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Role;
import eus.evernature.evern.models.forms.RoleUserForm;
import eus.evernature.evern.service.expert.ExpertServiceImpl;
import eus.evernature.evern.service.role.RoleServiceImpl;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    ExpertServiceImpl expertService;

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping("/experts")
    public ResponseEntity<List<Expert>> getUsers() {

        List<Expert> experts = expertService.getExperts();

        if (experts.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(experts);
    }

    @PostMapping("/expert/save")
    public ResponseEntity<Expert> saveUser(@RequestBody Expert expert) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(expertService.saveUser(expert));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserForm form) {
        expertService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    // @GetMapping(path = "/register/test", produces =
    // MediaType.APPLICATION_XML_VALUE)
    // public Expert getTestExpert() {
    // Expert expert = new Expert();

    // expert.setName("DE PRUEBA");
    // expert.setUsername("ELEPEPE");

    // return expert;
    // }

    // @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    // public Expert registeExpert(@RequestBody Expert expert) {
    // expert = expertRepository.save(expert);

    // return expert;
    // }

}
