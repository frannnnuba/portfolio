package com.store.api.DTOs;

import com.store.api.Entity.Rols;

public class RegisterUserRequest {
    private String username;
    private String password;
    private Rols rol;
  
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Rols getRol() {
        return rol;
    }
    public void setRol(Rols rol) {
        this.rol = rol;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    
}
