package com.ettarak.services.imp;

import com.ettarak.entities.Employee;
import com.ettarak.exceptions.EmployeeNotFound;
import com.ettarak.repositories.JpaEmployeeRepository;
import com.ettarak.responses.HttpResponse;
import com.ettarak.services.EmployeeService;
import com.ettarak.utils.DateTimeFormatter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImp implements EmployeeService {

    private final JpaEmployeeRepository jpaEmployeeRepository;

    @Override
    public HttpResponse<Employee> getEmployees() {
        log.info("Service : get Employees");
        List<Employee> employees = jpaEmployeeRepository.findAll();

        return HttpResponse.<Employee>builder()
                .data(employees)
                .message(!employees.isEmpty() ? (employees.size() + " employees retrieved") : "No data to display")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timStamp(toString())
                .build();
    }

    @Override
    public HttpResponse<Employee> getEmployee(Long id) throws EmployeeNotFound {
        log.info("Service : get Employee with id {}", id);
        Employee employee = jpaEmployeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFound("Employee with id " + id + " not found"));

        return HttpResponse.<Employee>builder()
                .data(Collections.singleton(employee))
                .message("Employee with id " + id + " retrieved")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timStamp(toString())
                .build();
    }

    @Override
    public HttpResponse<Employee> addEmployee(Employee employee) {
        log.info("Service : add Employee");
        Employee newEmployee = jpaEmployeeRepository.save(employee);

        return HttpResponse.<Employee>builder()
                .data(Collections.singleton(newEmployee))
                .message("Employee created with successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timStamp(toString())
                .build();
    }

    @Override
    public HttpResponse<Employee> updateEmployee(Long id, Employee employee) throws EmployeeNotFound {
        log.info("Service : update Employee with id {}", id);
        Employee updateEmployee = jpaEmployeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFound("Employee with id " + id + " not found"));

        updateEmployee.setEmployeeName(employee.getEmployeeName());
        updateEmployee.setEmployeeEmail(employee.getEmployeeEmail());

        jpaEmployeeRepository.save(updateEmployee);

        return HttpResponse.<Employee>builder()
                .data(Collections.singleton(updateEmployee))
                .message("Employee updated with successfully")
                .status(HttpStatus.ACCEPTED)
                .statusCode(HttpStatus.ACCEPTED.value())
                .timStamp(toString())
                .build();
    }

    @Override
    public HttpResponse<Employee> deleteEmployee(Long id) throws EmployeeNotFound {
        log.info("Service : delete Employee with id {}", id);
        Employee deleteEmployee = jpaEmployeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFound("Employee with id " + id + " not found"));

        jpaEmployeeRepository.delete(deleteEmployee);
        return HttpResponse.<Employee>builder()
                .data(Collections.singleton(deleteEmployee))
                .message("Employee deleted with successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timStamp(toString())
                .build();
    }

    @Override
    public String toString() {
        return  LocalDateTime.now().format(DateTimeFormatter.formatter);
    }
}
