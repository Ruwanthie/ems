package com.dasuni.emstask.taskdata.controller;

import com.dasuni.emstask.taskdata.service.TaskServiceImpl;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Project;
import com.dasuni.rentcloud.model.Task;
import com.dasuni.rentcloud.model.Telephone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tms")
public class TaskController {
    @Autowired
    TaskServiceImpl taskService;

    //To save the records to db - POST METHOD
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Task saveTask(@RequestBody Task task){
        return taskService.save(task);
    }

   //To update the records to db - PUT METHOD
    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Task update(@RequestBody Task newTask, @PathVariable Integer id) {

        return taskService.findById(id)
                .map(Task -> {
                    Task.setTask_desc(newTask.getTask_desc());
                    return taskService.save(Task);
                })
                .orElseGet(() -> {
                    newTask.setTid(id);
                    return taskService.save(newTask);
                });
    }

    //to fetch the all records - GET METHOD using jpa repository
    @RequestMapping(value = "/task", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public List<Task> fetchAll(Optional<Integer> id){
        return taskService.findAll();
    }

    //to fetch a particular record by id - GET METHOD using jpa repository
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public Optional<Task> fetchAll(@PathVariable Integer id){
        return taskService.findById(id);
    }
}
