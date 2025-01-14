package com.example.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CreateTaskRequest;
import com.example.demo.model.Task;
import com.example.demo.model.TaskResponse;
import com.example.demo.model.UpdateTaskRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private TaskResponse mapToTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPriority(task.getPriority());
        taskResponse.setStatus(task.getStatus());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(task.getUser().getId());
        userResponse.setName(task.getUser().getName());
        userResponse.setEmail(task.getUser().getEmail());

        taskResponse.setUser(userResponse);
        return taskResponse;
    }

    @Transactional
    public String create(CreateTaskRequest request) {
        User user = userRepository.findById(request.getUser_id()).get();

        Task task = new Task();
        task.setUser(user);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());

        taskRepository.save(task);
        return "Create task success";
    }

    @Transactional
    public List<TaskResponse> getByUser(int userId) {
        User user = userRepository.findById(userId).get();
        List<Task> tasks = taskRepository.findTaskbyUser(user);
        return tasks.stream().map(this::mapToTaskResponse).collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse getDetail(int id) {
        return mapToTaskResponse(taskRepository.findById(id).get());
    }

    @Transactional
    public Task update(int id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id).get();

        if (Objects.nonNull(request.getTitle())) {
            task.setTitle(request.getTitle());
        }
        if (Objects.nonNull(request.getDescription())) {
            task.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getStatus())) {
            task.setStatus(request.getStatus());
        }
        if (Objects.nonNull(request.getPriority())) {
            task.setPriority(request.getPriority());
        }

        return task;
    }

    @Transactional
    public String delete(int id) {
        Task task = taskRepository.findById(id).get();
        taskRepository.delete(task);
        return "Delete success";
    }
}
