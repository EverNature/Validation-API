package eus.evernature.evern.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.evernature.evern.filter.CustomAuthorizationFilter;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.service.role.RoleService;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    ExpertService expertService;

    @Autowired
    RoleService roleService;

    @Autowired
    CustomAuthorizationFilter customAuthorizationFilter;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) {

        try {
            customAuthorizationFilter.refreshAuthorizationToken(req, res);
        } catch (IOException e) {
            res.setHeader("error", e.getMessage());
            res.setStatus(HttpStatus.FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
