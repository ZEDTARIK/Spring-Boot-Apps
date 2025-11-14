package com.ettarak.resources;

import com.ettarak.entities.Employee;
import com.ettarak.exceptions.EmployeeNotFound;
import com.ettarak.responses.HttpResponse;
import com.ettarak.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/employees")
@RequiredArgsConstructor
public class EmployeeResource {

    private final EmployeeService employeeService;

    //@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE,  MediaType.APPLICATION_JSON_VALUE})
    @GetMapping( produces = {"application/json", "application/xml"})
    public ResponseEntity<HttpResponse<Employee>> RetrievedEmployees() {

        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<HttpResponse<Employee>> getEmployee(@PathVariable Long id) throws EmployeeNotFound {
        return ResponseEntity.ok().body(employeeService.getEmployee(id));
    }

    @PostMapping(
            produces = {"application/json", "application/xml"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<HttpResponse<Employee>> createEmployee(@RequestBody @Valid Employee employee) {
        return ResponseEntity
                .created(URI.create("/api/employees/" + employee.getId()))
                .body(employeeService.addEmployee(employee));
    }

    @PutMapping(path = "/{id}",
            produces = {"application/json", "application/xml"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<HttpResponse<Employee>> updateEmployee(@PathVariable Long id,
                                                                 @RequestBody @Valid Employee employee) {
        return ResponseEntity.accepted().body(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpResponse<Employee>> deleteEmployee(@PathVariable Long id) throws EmployeeNotFound {
        return ResponseEntity.ok().body(employeeService.deleteEmployee(id));
    }

}
