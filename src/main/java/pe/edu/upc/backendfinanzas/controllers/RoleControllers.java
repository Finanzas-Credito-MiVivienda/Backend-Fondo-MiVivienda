package pe.edu.upc.backendfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.entities.Role;
import pe.edu.upc.backendfinanzas.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleControllers {
    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = true)
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.OK);
    }
}