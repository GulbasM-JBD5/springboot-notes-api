package com.example.thirdyear.service;

import com.example.thirdyear.dto.TaskRequest;
import com.example.thirdyear.entity.Task;
import com.example.thirdyear.entity.User;
import com.example.thirdyear.repository.TaskRepository;
import com.example.thirdyear.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService{
    private final TaskRepository taskrepository;
    private final UserRepository userRepository;
    public TaskService(TaskRepository taskrepository,UserRepository userRepository){
        this.taskrepository=taskrepository;
        this.userRepository=userRepository;
    }
public void addTask(TaskRequest request) {
    //demeli email aldim authenticationdan  ona aid  user tapiram
    //sonra istifadeciden gelecek  olani  task entitye cevirecem post edecem bazaya sonra da response qaytarmiram yox

    //yaratmaliyam task entity
    User user = getCurrentUser();
    Task task = new Task();
    task.setTitle(request.getTitle());
    task.setUser(user);
    task.setCompleted(false);
    taskrepository.save(task);
}
    // niye methoddan xaroicde?
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User tapılmadı."));//ziyafetokay
        return user;
    }
    //delete,get methodu
    public void deleteTask(Long id){
        // userin id sine gore  yox taskin issine baxariamsilirem ha
        User user=getCurrentUser();
        Task task=taskrepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task tapılmadı."));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Sizin bu task-ı silməyə icazəniz yoxdur!");
        }
                taskrepository.delete(task);
    }
    public List<Task> tasks showAllTasks() {
     User user=getCurrentUser();
     //response hazirlamaliyam da
    }

    }