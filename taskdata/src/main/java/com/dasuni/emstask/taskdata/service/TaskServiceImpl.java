package com.dasuni.emstask.taskdata.service;


import com.dasuni.emstask.taskdata.repository.TaskRepository;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public List<Task> getTasksList(List<Integer> ids){
        return taskRepository.findByIdIn(ids);
    }

}
