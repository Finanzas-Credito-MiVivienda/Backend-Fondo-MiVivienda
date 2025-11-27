package pe.edu.upc.backendfinanzas.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.upc.backendfinanzas.entities.Role;

@Data
@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {
    private Role authority;

    @Override
    public String getAuthority() {
        return authority.getNameRol();
    }
}