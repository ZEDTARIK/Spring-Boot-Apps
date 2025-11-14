package com.ettarak;

import com.ettarak.entities.Employee;
import com.ettarak.repositories.JpaEmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner start(JpaEmployeeRepository jpaEmployeeRepository) {
        return (args) -> {
            jpaEmployeeRepository.save(Employee.builder().employeeName("Zouhair").employeeEmail("ettarak@gmail.com").build());
            jpaEmployeeRepository.save(Employee.builder().employeeName("John").employeeEmail("John@gmail.com").build());
        };
    }
}
