package com.dasuni.emsemp.empdata.service;

import com.dasuni.emsemp.empdata.repository.EmployeeRepository;
import com.dasuni.rentcloud.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee update(Employee newEmployee, Integer id) {

        return employeeRepository.findById(id)
                .map(Student -> {
                    Student.setName(newEmployee.getName());
                    Student.setAddress(newEmployee.getAddress());
                    Student.setTelephoneList(newEmployee.getTelephoneList());
                    return employeeRepository.save(Student);
                })
                .orElseGet(() -> {
                    newEmployee.setEmpid(id);
                    return employeeRepository.save(newEmployee);
                });

    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Integer id){
        return employeeRepository.findById(id);
    }

}
