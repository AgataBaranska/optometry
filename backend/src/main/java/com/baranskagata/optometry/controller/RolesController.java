package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/roles")
public class RolesController {

    private final RoleService rolesService;


    @GetMapping
    ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(rolesService.getRoles());
    }

    @GetMapping("{name}")
    ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok().body(rolesService.getRoleByName(name));
    }


    @PostMapping()
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles").toUriString());
        return ResponseEntity.created(uri).body(rolesService.saveRole(role));
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleData) {
        return ResponseEntity.ok().body(rolesService.updateRole(id, roleData));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


}
