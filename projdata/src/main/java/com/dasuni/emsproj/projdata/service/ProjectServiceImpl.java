package com.dasuni.emsproj.projdata.service;

import com.dasuni.emsproj.projdata.repository.ProjectRepository;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl {
    @Autowired
    ProjectRepository projectRepository;

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public Project update(Project newProject, Integer id) {

        return projectRepository.findById(id)
                .map(Project -> {
                    Project.setName(newProject.getName());
                    Project.setProj_desc(newProject.getProj_desc());
                    return projectRepository.save(Project);
                })
                .orElseGet(() -> {
                    newProject.setPid(id);
                    return projectRepository.save(newProject);
                });

    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Integer id){
        return projectRepository.findById(id);
    }

}
