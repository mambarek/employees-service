package com.it2go.micro.employeesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class EmployeesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesServiceApplication.class, args);
    }

}
