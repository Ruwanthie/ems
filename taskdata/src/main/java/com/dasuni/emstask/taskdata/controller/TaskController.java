package com.dasuni.emstask.taskdata.controller;

import com.dasuni.emstask.taskdata.service.TaskServiceImpl;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ems")
public class TaskController {

    @Autowired
    TaskServiceImpl taskService;

    @RequestMapping("/tasks")
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    @RequestMapping(value = "/tasks",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('write_profile')")
    public Task save(@RequestBody Task task){
        return taskService.save(task);
    }

    @RequestMapping(value = "/tasks/{ids}",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Task> getTask(@PathVariable List<Integer> ids){
        return taskService.getTasksList(ids);
    }


}
