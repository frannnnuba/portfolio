package com.store.api.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Users /*implements UserDetails*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usern_name;

    @Column(nullable = false)
    private String first_name;
    
    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String mail_direction;

    @Column(nullable = false)
    private String phone_number;

    @OneToOne
    private Cart cart;

    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL,orphanRemoval=true)
    private Set<Order> orders;
}
