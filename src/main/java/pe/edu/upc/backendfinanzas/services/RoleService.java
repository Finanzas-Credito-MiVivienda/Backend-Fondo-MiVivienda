package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.entities.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAllRoles();
}