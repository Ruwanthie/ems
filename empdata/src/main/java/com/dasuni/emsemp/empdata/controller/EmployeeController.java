package com.dasuni.emsemp.empdata.controller;

import com.dasuni.emsemp.empdata.service.EmployeeServiceImpl;
import com.dasuni.rentcloud.model.AssignTask;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Project;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/ems")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @RequestMapping(value = "/employees")
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @RequestMapping(value = "/employees",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_manager')")
    public Employee save(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @RequestMapping(value = "/employees/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Employee getEmployee(@PathVariable Integer id){
        return employeeService.getEmployee(id);
    }

    @RequestMapping(value = "/employees/{id}/projects",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Project> getProjectIds(@PathVariable Integer id){
        return employeeService.getProjects(id);
    }

    @RequestMapping(value = "/employees/{eid}/projects/{pid}/tasks",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Task> getProjectIds(@PathVariable Integer eid, @PathVariable Integer pid){
        return employeeService.getTasks(pid);
    }

    @RequestMapping(value = "/assign",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_manager')")
    public List<AssignTask> saveAssignTask(@RequestBody List<AssignTask> assignTasks){
        return employeeService.saveAssignTask(assignTasks);
    }
}
