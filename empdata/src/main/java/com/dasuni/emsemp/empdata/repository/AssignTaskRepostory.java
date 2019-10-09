package com.dasuni.emsemp.empdata.repository;

import com.dasuni.rentcloud.model.AssignTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignTaskRepostory extends JpaRepository<AssignTask,Integer> {
    List<AssignTask> findByEid(Integer eid);
    List<AssignTask> findByPid(Integer pid);
}
