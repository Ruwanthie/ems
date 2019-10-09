package com.dasuni.emsproj.projdata.repository;

import com.dasuni.rentcloud.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByIdIn(List<Integer> id);
}
