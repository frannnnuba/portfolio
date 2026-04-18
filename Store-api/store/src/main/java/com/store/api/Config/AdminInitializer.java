package com.store.api.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.store.api.Entity.Rols;
import com.store.api.Entity.Users;
import com.store.api.Repository.userDetailsRepository;

@Component
public class AdminInitializer {
    @Bean
    public CommandLineRunner admin(userDetailsRepository repo,PasswordEncoder passwordEncoder){
        return args ->{
            if(!repo.findByUsername("admin").isPresent()){
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword((passwordEncoder.encode("probando123")));
                admin.setRol(Rols.ADMIN);
                admin.setFirstname("Francisco");
                admin.setLastname("Suarez");
                admin.setPhonenumber("2994689140");
                admin.setMaildirection("mymail@yahoo.com");
                repo.save(admin);
            }
        };
    }

}
