package com.baranskagata.optometry.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<Page<AppUser>> getAllUsers(@RequestParam(required = false) String lastName, Pageable pageable) {
        if(lastName ==null) {
            return ResponseEntity.ok().body(userService.getUsers(pageable));
        }else{

            return ResponseEntity.ok().body( userService.getUsersByLastName(lastName,pageable));

        }
    }

    @GetMapping({"{username}"})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping()
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PutMapping({"{username}"})
    public ResponseEntity<AppUser> updateUser(@PathVariable String username, @RequestBody AppUser userData){
        return ResponseEntity.ok().body(userService.updateUser(username, userData));
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //Roles
    @GetMapping("{username}/roles")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserRoles(username));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllAppRoles(){
        return ResponseEntity.ok().body(userService.getAllAppRoles());
    }

    @PostMapping("{username}/roles")
    public ResponseEntity<HttpStatus> addRoleToUser(@RequestParam String username,  @RequestParam String roleName) {
        userService.addRoleToUser(username,roleName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{username}/roles/{roleName}")
    public ResponseEntity<HttpStatus> removeRoleFromUser(@PathVariable  String username,@PathVariable String roleName){
        userService.removeRoleFromUser(username,roleName);
        return  ResponseEntity.noContent().build();
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





