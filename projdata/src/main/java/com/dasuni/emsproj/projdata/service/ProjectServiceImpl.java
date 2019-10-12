package com.dasuni.emsproj.projdata.service;

import com.dasuni.emsproj.projdata.repository.ProjectRepository;
import com.dasuni.rentcloud.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public List<Project> getProjectsList(List<Integer> ids){
        return projectRepository.findByIdIn(ids);
    }

}
