package eus.evernature.evern.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eus.evernature.evern.filter.CustomAuthorizationFilter;
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

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) {

        try {
            CustomAuthorizationFilter.refreshAuthorizationToken(req, res, expertService);
        } catch (IOException e) {
            res.setHeader("error", e.getMessage());
            res.setStatus(HttpStatus.FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
