package com.dasuni.emsui.emsui.controller;

import com.dasuni.emsui.emsui.conf.AccessTokenConfigurer;
import com.dasuni.rentcloud.model.Employee;
import com.dasuni.rentcloud.model.Project;
import com.dasuni.rentcloud.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;


import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@Controller
@EnableOAuth2Sso
public class EMSUIController extends WebSecurityConfigurerAdapter{
    @Autowired
    RestTemplate restTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated();
    }


    @RequestMapping(value = "/")
    public String loadMain(){
        return "index";
    }

    @RequestMapping(value = "/home")
    public String loadIndex(){
        return "home";
    }

    @RequestMapping(value = "/emp")
    public String loadEmployee(Model model){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Employee[]> responseEntity = restTemplate.exchange("http://localhost:8083/ems/employee", HttpMethod.GET, employeeHttpEntity, Employee[].class);
            model.addAttribute("employees", responseEntity.getBody());
        }catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return "empinfo";
    }

    @RequestMapping(value = "/emp/data")
    public String loadEmployeeinfo(){
        return "empinfo";
    }

    @RequestMapping(value = "/project")
    public String loadProject(Model model){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Project> projectHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Project[]> responseEntity = restTemplate.exchange("http://localhost:8084/pms/project", HttpMethod.GET, projectHttpEntity, Project[].class);
            model.addAttribute("projects", responseEntity.getBody());
        }catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return "projectinfo";
    }

    @RequestMapping(value = "/task")
    public String loadTask(Model model){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessTokenConfigurer.getToken());
        HttpEntity<Task> taskHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Task[]> responseEntity = restTemplate.exchange("http://localhost:8085/tms/task", HttpMethod.GET, taskHttpEntity, Task[].class);
            model.addAttribute("tasks", responseEntity.getBody());
        }catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return "taskinfo";
    }

}
