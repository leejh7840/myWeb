package com.jaylee.demo.service;

import com.jaylee.demo.model.Role;
import com.jaylee.demo.model.User;
import com.jaylee.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User save(User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
