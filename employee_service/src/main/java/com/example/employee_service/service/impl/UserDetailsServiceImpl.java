package com.example.employee_service.service.impl;

import com.example.employee_service.entity.UsersEntity;
import com.example.employee_service.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        private final UsersRepository usersRepository;

        public UserDetailsServiceImpl(UsersRepository usersRepository) {
                this.usersRepository = usersRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username)
                        throws UsernameNotFoundException {
                UsersEntity user = usersRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User tidak ditemukan: " + username));

                return new User(
                                user.getUsername(),
                                user.getPassword(),
                                Collections.singletonList(
                                                new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName())));
        }
}