package com.dasuni.emsproj.projdata.service;

import com.dasuni.emsproj.projdata.exception.ProjectNotFoundException;
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

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public Project getProject(Integer id){
        Optional<Project> optionalStudent = projectRepository.findById(id);


        if(!optionalStudent.isPresent()){
            throw new ProjectNotFoundException("Invalid Project ID");
        }
        return optionalStudent.get();
    }

    public List<Project> getProjectsList(List<Integer> ids){
        return projectRepository.findByIdIn(ids);
    }

}
