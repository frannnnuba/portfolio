package com.store.api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.api.Repository.userDetailsRepository;

@Service
public class customUserDetailsService implements UserDetailsService  {

    @Autowired
    private userDetailsRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException(""));
    }

}
