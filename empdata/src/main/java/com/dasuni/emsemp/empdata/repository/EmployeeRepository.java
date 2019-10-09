package com.dasuni.emsemp.empdata.repository;

import com.dasuni.rentcloud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
