package com.store.api.DTOs;

import com.store.api.Entity.Cart;
import com.store.api.Entity.Rols;

public class UserResponse {
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String mail_direction;
    private String phone_number;
    private Cart cart;
    private Rols rol;
    public UserResponse(Long id, String username,String first_name, String last_name, String mail_direction,
            String phone_number, Cart cart,Rols rol) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mail_direction = mail_direction;
        this.phone_number = phone_number;
        this.cart = cart;
        this.rol=rol;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

     public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getMail_direction() {
        return mail_direction;
    }
    public void setMail_direction(String mail_direction) {
        this.mail_direction = mail_direction;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Rols getRol() {
        return rol;
    }
    public void setRol(Rols rol) {
        this.rol = rol;
    }

}
