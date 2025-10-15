package com.fixmystreet.fixmystreet.services.impl;

import com.fixmystreet.fixmystreet.exceptions.ResourceNotFoundException;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username).orElseThrow(() ->
               new ResourceNotFoundException("User not found with username: " + username));
    }
}
