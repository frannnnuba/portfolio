package com.store.api.Entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name= "first_name",nullable = false)
    private String firstname;
    
    @Column(name= "last_name",nullable = false)
    private String lastname;

    @Column(name =  "mail_direction",nullable = false)
    private String maildirection;

    @Column(name="phone_number",nullable = false)
    private String phonenumber;

    @OneToOne
    private Cart cart;

    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL,orphanRemoval=true)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rols rol;


     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ rol.name()));
        Set<SimpleGrantedAuthority> permissionAuthorities = rol.getPermissions().stream().
        map(permisos-> new SimpleGrantedAuthority(permisos.name())).collect(Collectors.toSet());
        authorities.addAll(permissionAuthorities);
        return authorities;
    }

    @Override
    public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}

    @Override
    public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

    @Override
    public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

    @Override
    public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}
}
