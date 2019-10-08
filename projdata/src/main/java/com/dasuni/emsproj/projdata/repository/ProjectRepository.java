package com.dasuni.emsproj.projdata.repository;

import com.dasuni.rentcloud.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
