package com.dasuni.emstask.taskdata.service;

import com.dasuni.emstask.taskdata.repository.TaskRepository;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl {
    @Autowired
    TaskRepository taskRepository;

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public Task update(Task newTask, Integer id) {

        return taskRepository.findById(id)
                .map(Task -> {
                    Task.setTask_desc(newTask.getTask_desc());
                    return taskRepository.save(Task);
                })
                .orElseGet(() -> {
                    newTask.setTid(id);
                    return taskRepository.save(newTask);
                });

    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Integer id){
        return taskRepository.findById(id);
    }
}
