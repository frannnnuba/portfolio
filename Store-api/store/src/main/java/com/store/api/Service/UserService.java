package com.store.api.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.api.DTOs.RegisterUserRequest;
import com.store.api.DTOs.UserResponse;
import com.store.api.Entity.Users;
import com.store.api.Repository.userDetailsRepository;

@Service
public class UserService {
    private final userDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(userDetailsRepository userDetailsRepository,PasswordEncoder passwordEncoder){
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder= passwordEncoder;
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest){
        if(userDetailsRepository.findByUsername(registerUserRequest.getUsername()).
        isPresent()){
            throw new RuntimeException("Usuario no se puede registrar");    
        }

        Users user = new Users();
        user.setUsername(registerUserRequest.getUsername());;
        user.setRol(registerUserRequest.getRol());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        
        Users savedUser = userDetailsRepository.save(user);
        return new UserResponse(savedUser.getId(),savedUser.getUsername(),savedUser.getFirstname(), savedUser.getLastname(), savedUser.getMaildirection(), savedUser.getPhonenumber(), savedUser.getCart(), savedUser.getRol());
    }
}
