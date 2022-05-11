package eus.evernature.evern.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Role;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.utility.JwtAlgorithmProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    
    public static void refreshAuthorizationToken(HttpServletRequest request, HttpServletResponse response, ExpertService expertService)
            throws StreamWriteException, DatabindException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new RuntimeException("Refresh token is missing");

        try {
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = JwtAlgorithmProvider.getHMAC256A();

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();

            Expert expert = expertService.getExpert(username);

            String acces_token = JWT.create()
                    .withSubject(expert.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", expert.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("acces_token", acces_token);
            tokenMap.put("refresh_token", refresh_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        } catch (JWTVerificationException e) {
            log.error("Error loggin in: {}", e.getMessage());
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") ||request.getServletPath().equals("/token/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = JwtAlgorithmProvider.getHMAC256A();

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            try {
                Stream.of(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
            } catch (Exception e) {
                log.info("User logged without roles");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            log.error("Error loggin in: {}", e.getMessage());
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
}
