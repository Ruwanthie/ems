package com.dasuni.emsui.emsui.controller;

import com.dasuni.emsui.emsui.conf.AccessTokenConfigurer;
import com.dasuni.rentcloud.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;



@Controller
@EnableOAuth2Sso
public class EMSUIController extends WebSecurityConfigurerAdapter{
    @Autowired
    RestTemplate restTemplate;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/logout").permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        http.sessionManagement().maximumSessions(1);


    }



    @RequestMapping(value = "/")
    public String loadMain(){
        return "home";
    }


    @RequestMapping(value = "/home")
    public String loadIndex(){
        return "dashboard";
    }

    @RequestMapping(value = "/employees")
    public String getEmployees(Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(httpHeaders);
        try {
            ResponseEntity<Employee[]> responseEntity = restTemplate.exchange("http://empservice:8083/ems/employees", HttpMethod.GET,employeeHttpEntity,Employee[].class);
            model.addAttribute("employees",responseEntity.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }
        return "list-employee";
    }

    @RequestMapping(value = "/employees",method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute Employee employee,Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(employee,httpHeaders);

        try {
            ResponseEntity<Employee> responseEntity = restTemplate.exchange("http://empservice:8083/ems/employees", HttpMethod.POST,employeeHttpEntity,Employee.class);
        } catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "redirect:employees";
    }

    @RequestMapping(value = "/add-employee")
    public String createEmployee(){
        return "add-employee";
    }

    @RequestMapping(value = "/projects")
    public String getProjects(Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Project> projectHttpEntity = new HttpEntity<Project>(httpHeaders);
        try {
            ResponseEntity<Project[]> responseEntity = restTemplate.exchange("http://projectservice:8084/ems/projects", HttpMethod.GET,projectHttpEntity,Project[].class);
            model.addAttribute("projects",responseEntity.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }
        return "list-project";
    }

    @RequestMapping(value = "/projects",method = RequestMethod.POST)
    public String saveProject(@ModelAttribute Project project,Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Project> projectHttpEntity = new HttpEntity<Project>(project,httpHeaders);

        try {
            ResponseEntity<Project> responseEntity = restTemplate.exchange("http://projectservice:8084/ems/projects", HttpMethod.POST,projectHttpEntity,Project.class);
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "redirect:projects";
    }

    @RequestMapping(value = "/add-project")
    public String createProject(){
        return "add-project";
    }

    @RequestMapping(value = "/tasks")
    public String getTasks(Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Task> taskHttpEntity = new HttpEntity<Task>(httpHeaders);
        try {
            ResponseEntity<Task[]> responseEntity = restTemplate.exchange("http://taskservice:8085/ems/tasks", HttpMethod.GET,taskHttpEntity,Task[].class);
            model.addAttribute("tasks",responseEntity.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }
        return "list-task";
    }

    @RequestMapping(value = "/tasks",method = RequestMethod.POST)
    public String saveTask(@ModelAttribute Task task,Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Task> taskHttpEntity = new HttpEntity<Task>(task,httpHeaders);
        try {
            ResponseEntity<Task> responseEntity = restTemplate.exchange("http://taskservice:8085/ems/tasks", HttpMethod.POST,taskHttpEntity,Task.class);
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "redirect:tasks";
    }

    @RequestMapping(value = "/add-task")
    public String createTask(){
        return "add-task";
    }

    @RequestMapping(value = "/operations")
    public String doOperations(Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(httpHeaders);
        try {
            ResponseEntity<Employee[]> responseEntity1 = restTemplate.exchange("http://empservice:8083/ems/employees", HttpMethod.GET,employeeHttpEntity,Employee[].class);
            model.addAttribute("employees",responseEntity1.getBody());
            HttpEntity<Project> projectHttpEntity = new HttpEntity<Project>(httpHeaders);
            ResponseEntity<Project[]> responseEntity2 = restTemplate.exchange("http://projectservice:8084/ems/projects", HttpMethod.GET,projectHttpEntity,Project[].class);
            model.addAttribute("projects",responseEntity2.getBody());
            HttpEntity<Task> taskHttpEntity = new HttpEntity<Task>(httpHeaders);
            ResponseEntity<Task[]> responseEntity3 = restTemplate.exchange("http://taskservice:8085/ems/tasks", HttpMethod.GET,taskHttpEntity,Task[].class);
            model.addAttribute("tasks",responseEntity3.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }
        return "operation";
    }

    @RequestMapping(value = "/assign",method = RequestMethod.POST)
    public String assignProject(@ModelAttribute AssignTaskList assignTaskList, Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        List<AssignTask> assignTask = new ArrayList<>();
        assignTaskList.getTid().forEach((t)->{
            AssignTask task = new AssignTask();
            task.setEid(assignTaskList.getEid());
            task.setPid(assignTaskList.getPid());
            task.setTid(t);
            assignTask.add(task);
        });

        HttpEntity<List> assignTaskHttpEntity = new HttpEntity<List>(assignTask,httpHeaders);
        try {
            ResponseEntity<List> responseEntity = restTemplate.exchange("http://empservice:8083/ems/assign", HttpMethod.POST,assignTaskHttpEntity,List.class);
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "redirect:operations";
    }

    @RequestMapping(value = "/employees/{id}")
    public String getEmployee(@PathVariable Integer id , Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(httpHeaders);

        try {
            ResponseEntity<Employee> responseEntity1 = restTemplate.exchange("http://empservice:8083/ems/employees/{id}", HttpMethod.GET,employeeHttpEntity,Employee.class,id);
            model.addAttribute("employee",responseEntity1.getBody());
            ResponseEntity<Project[]> responseEntity2 = restTemplate.exchange("http://empservice:8083/ems/employees/{id}/projects", HttpMethod.GET,employeeHttpEntity,Project[].class,id);
            model.addAttribute("projects",responseEntity2.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "viewinfo";
    }

    @RequestMapping(value = "/employees/{id}/projects")
    public String getEmployeeProjects(@PathVariable Integer id ,Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(httpHeaders);
        try {
            ResponseEntity<Project[]> responseEntity = restTemplate.exchange("http://empservice:8083/ems/employees/{id}/projects", HttpMethod.GET,employeeHttpEntity,Project[].class,id);
            model.addAttribute("projects",responseEntity.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "viewProjects";
    }

    @RequestMapping(value = "/employees/{eid}/projects/{pid}/tasks")
    public String getEmployeeProjects(@PathVariable Integer eid ,@PathVariable Integer pid,Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(httpHeaders);

        try {
            ResponseEntity<Task[]> responseEntity = restTemplate.exchange("http://empservice:8083/ems/employees/{eid}/projects/{pid}/tasks", HttpMethod.GET,employeeHttpEntity,Task[].class,eid,pid);
            model.addAttribute("tasks",responseEntity.getBody());
        }catch (HttpStatusCodeException se){
            ResponseEntity responseEntity = ResponseEntity.status(se.getRawStatusCode()).headers(se.getResponseHeaders()).body(se.getResponseBodyAsString());
            model.addAttribute("error",responseEntity);
        }

        return "assign-tasks";
    }

}
