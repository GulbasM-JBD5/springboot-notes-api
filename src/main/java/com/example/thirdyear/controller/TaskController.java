package com.example.thirdyear.controller;

import com.example.thirdyear.dto.TaskRequest;
import com.example.thirdyear.entity.Task;
import com.example.thirdyear.entity.User;
import com.example.thirdyear.repository.TaskRepository;
import com.example.thirdyear.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tasks")
@RestController
public class TaskController {
    TaskService taskservice;
    public TaskController(TaskService taskservice){
        this.taskservice=taskservice;
    }
    @PostMapping
    public void addTask(@Valid @RequestBody TaskRequest request){
        taskservice.addTask(request);
    }
    @DeleteMapping("/id")
    public void deleteTask(Long id){
        taskservice.deleteTask(id);
    }
    @GetMapping
    public List<Task> tasks showAllTasks(){
        taskservice.showALlTasks();
    }






}
