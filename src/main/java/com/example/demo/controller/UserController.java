package com.example.demo.controller;

import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserResponse;
import com.example.demo.model.WebResponse;
import com.example.demo.service.UserService;

import jakarta.websocket.server.PathParam;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody RegisterUserRequest request) {
        return userService.register(request);
    }

    @GetMapping("/api/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/api/users/{id}")
    public User get(@PathVariable int id) {
        return userService.get(id);
    }

    @GetMapping("/api/users/name")
    public User getByName(@RequestParam String key) {
        return userService.getByName(key);
    }

    @PatchMapping(path = "/api/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/api/users/{id}")
    public String delete(@PathVariable int id) {
        return userService.delete(id);
    }
}
