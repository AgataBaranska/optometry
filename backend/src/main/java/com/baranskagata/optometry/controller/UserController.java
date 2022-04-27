package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<Page<AppUser>> getUsers(Pageable pageable){
        return ResponseEntity.ok().body(userService.getUsers(pageable));
    }
    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/roles/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        userService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
    return ResponseEntity.ok().build();
    }

}

@Data
class RoleUserForm{
    private String username;
    private String roleName;
}
