package com.dasuni.emsemp.empdata.service;

import com.dasuni.emsemp.empdata.exception.EmployeeNotFoundException;
import com.dasuni.emsemp.empdata.repository.AssignTaskRepostory;
import com.dasuni.emsemp.empdata.repository.EmployeeRepository;
import com.dasuni.rentcloud.model.AssignTask;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Project;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AssignTaskRepostory assignTaskRepostory;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            throw new EmployeeNotFoundException("Invalid Employee ID");
        }
        return employee.get();
    }

    public List<AssignTask> saveAssignTask(List<AssignTask> assignTasks){
        return assignTaskRepostory.saveAll(assignTasks);
    }

    public List<Project> getProjects(Integer eid){
        List<AssignTask> assignTasks = assignTaskRepostory.findByEid(eid);

        String projectIds = assignTasks.stream().map(s->String.valueOf(s.getPid())).collect(Collectors.joining(","));

        if(projectIds.equals(null)||projectIds.equals("")){

            return null;

        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
            HttpEntity<Project> projectHttpEntity = new HttpEntity<Project>(httpHeaders);
            ResponseEntity<List> responseEntity = restTemplate.exchange("http://projectservice:8084/ems/projects/{ids}", HttpMethod.GET, projectHttpEntity, List.class, projectIds);

            return responseEntity.getBody();
        }
    }

    public List<Task> getTasks(Integer pid){
        List<AssignTask> assignTasks = assignTaskRepostory.findByPid(pid);
        String taskIds = assignTasks.stream().map(s->String.valueOf(s.getTid())).collect(Collectors.joining(","));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Task> taskHttpEntity = new HttpEntity<Task>(httpHeaders);
        ResponseEntity<List> responseEntity = restTemplate.exchange("http://taskservice:8085/ems/tasks/{ids}", HttpMethod.GET, taskHttpEntity, List.class, taskIds);

        return responseEntity.getBody();

    }
}
