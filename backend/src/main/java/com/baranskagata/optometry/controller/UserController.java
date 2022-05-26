package com.baranskagata.optometry.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<AppUser>> getUsers(Pageable pageable) {
        return ResponseEntity.ok().body(userService.getUsers(pageable));
    }

    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PostMapping("/roles/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        userService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String refresh_token = requestMap.get("refresh_token");
            log.info("In refresh token: " + refresh_token);


            //TODO utlity class for secret and alhorithm
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();
            AppUser user = userService.getUser(username);

            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            String access_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);


            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);

        } catch (Exception exception) {
            response.setHeader("error", exception.getMessage());
            //  response.sendError(FORBIDDEN.value());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

    }
}


@Data
class RoleUserForm {
    private String username;
    private String roleName;
}


