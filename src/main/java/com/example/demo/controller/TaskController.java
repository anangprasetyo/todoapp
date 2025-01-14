package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CreateTaskRequest;
import com.example.demo.model.Task;
import com.example.demo.model.TaskResponse;
import com.example.demo.model.UpdateTaskRequest;
import com.example.demo.service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(path = "/api/task", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public String create(@RequestBody CreateTaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping("/api/task/{id}")
    @PreAuthorize("hasRole('USER')")
    public TaskResponse get(@PathVariable int id) {
        return taskService.getDetail(id);
    }

    @GetMapping("/api/task/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public List<TaskResponse> getByUser(@PathVariable int userId) {
        return taskService.getByUser(userId);
    }

    @PatchMapping(path = "/api/task/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public Task update(@PathVariable int id, @RequestBody UpdateTaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/api/task/{id}")
    @PreAuthorize("hasRole('USER')")
    public String delete(@PathVariable int id) {
        return taskService.delete(id);
    }

}
