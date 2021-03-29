package com.example.demo.users;

import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class User {

    private final int userId;

    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

    @NotBlank
    private final boolean enabled;


    private Collection<GrantedAuthority> roles;

    public User(int userId, @NotBlank String username, @NotBlank String password, @NotBlank boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(int id, String userName, String password, boolean enabled, Collection<GrantedAuthority> grantedAuthorities) {
        this(id, userName, password, enabled);
        setRoles(grantedAuthorities);
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
