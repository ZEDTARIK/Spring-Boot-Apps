package com.ettarak.services;

import com.ettarak.entities.Employee;
import com.ettarak.responses.HttpResponse;

public interface EmployeeService {
    HttpResponse<Employee> getEmployees();
    HttpResponse<Employee> getEmployee(Long id);
    HttpResponse<Employee> addEmployee(Employee employee);
    HttpResponse<Employee> updateEmployee(Long id, Employee employee);
    HttpResponse<Employee> deleteEmployee(Long id);
}
