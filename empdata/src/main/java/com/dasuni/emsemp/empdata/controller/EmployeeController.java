package com.dasuni.emsemp.empdata.controller;

import com.dasuni.emsemp.empdata.service.EmployeeServiceImpl;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Telephone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ems")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    //To save the records to db - POST METHOD
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Employee saveEmployee(@RequestBody Employee employee){

        for (Telephone tel: employee.getTelephoneList()) {
            tel.setEmployee(employee);
        }
        return employeeService.save(employee);
    }

    //To update the records to db - PUT METHOD
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_admin')")
    public Employee update(@RequestBody Employee newEmployee, @PathVariable Integer id) {

        return employeeService.findById(id)
                .map(Employee -> {
                    Employee.setName(newEmployee.getName());
                    Employee.setAddress(newEmployee.getAddress());
                    Employee.setTelephoneList(newEmployee.getTelephoneList());
                    for (Telephone tel: newEmployee.getTelephoneList()) {
                        tel.setEmployee(newEmployee);
                    }
                    return employeeService.save(Employee);
                })
                .orElseGet(() -> {
                    newEmployee.setEmpid(id);
                    return employeeService.save(newEmployee);
                });
    }

    //to fetch the all records - GET METHOD using jpa repository
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public List<Employee> fetchAll(Optional<Integer> id){
        return employeeService.findAll();
    }

    //to fetch a particular record by id - GET METHOD using jpa repository
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public Optional<Employee> fetchAll(@PathVariable Integer id){
        return employeeService.findById(id);
    }

}
