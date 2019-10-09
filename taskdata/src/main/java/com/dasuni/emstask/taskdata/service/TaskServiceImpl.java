package com.dasuni.emstask.taskdata.service;

import com.dasuni.emstask.taskdata.exception.TaskNotFoundException;
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

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task getTask(Integer id){
        Optional<Task> optionalStudent = taskRepository.findById(id);
        if(!optionalStudent.isPresent()){
            throw new TaskNotFoundException("Invalid Task ID");
        }
        return optionalStudent.get();
    }

    public List<Task> getTasksList(List<Integer> ids){
        return taskRepository.findByIdIn(ids);
    }

}
