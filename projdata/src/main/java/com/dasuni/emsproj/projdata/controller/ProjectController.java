package com.dasuni.emsproj.projdata.controller;

import com.dasuni.emsproj.projdata.service.ProjectServiceImpl;
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
@RequestMapping(value = "/pms")
public class ProjectController {
    @Autowired
    ProjectServiceImpl projectService;

    //To save the records to db - POST METHOD
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Project saveProject(@RequestBody Project project){


        return projectService.save(project);
    }

    //To update the records to db - PUT METHOD
    @RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Project update(@RequestBody Project newProject, @PathVariable Integer id) {

        return projectService.findById(id)
                .map(Project -> {
                    Project.setName(newProject.getName());
                    Project.setProj_desc(newProject.getProj_desc());
                    return projectService.save(Project);
                })
                .orElseGet(() -> {
                    newProject.setPid(id);
                    return projectService.save(newProject);
                });
    }

    //to fetch the all records - GET METHOD using jpa repository
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public List<Project> fetchAll(Optional<Integer> id){
        return projectService.findAll();
    }

    //to fetch a particular record by id - GET METHOD using jpa repository
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public Optional<Project> fetchAll(@PathVariable Integer id){
        return projectService.findById(id);
    }
}
