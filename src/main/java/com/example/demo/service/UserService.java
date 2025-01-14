package com.example.demo.service;

import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserResponse;
import com.example.demo.model.WebResponse;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String register(RegisterUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return "Create success";
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User get(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public User update(int id, UpdateUserRequest request) {
        User user = userRepository.findById(id).get();

        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        }
        return user;
    }

    @Transactional
    public String delete(int id) {
        User user = userRepository.findById(id).get();

        userRepository.delete(user);
        return "Delete sucess";
    }
}
