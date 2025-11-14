package com.ettarak;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.ettarak.entities.Employee;
import com.ettarak.resources.EmployeeResource;
import com.ettarak.responses.HttpResponse;
import com.ettarak.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeResource.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void retrievedEmployees_returnsWrappedResponse() throws Exception {
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setEmployeeName("Zouhair");
        e1.setEmployeeEmail("ettarak@gmail.com");

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setEmployeeName("John");
        e2.setEmployeeEmail("John@gmail.com");

        HttpStatus status = HttpStatus.OK;

        HttpResponse<Employee> mockResponse = HttpResponse.<Employee>builder()
                .timStamp("2025-11-14 15:40:45")
                .status(status)
                .statusCode(status.value())
                .message("2 employees retrieved")
                .data(List.of(e1, e2))
                .build();

        when(employeeService.getEmployees()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/employees").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("2 employees retrieved"))
                .andExpect(jsonPath("$.data[0].employeeName").value("Zouhair"))
                .andExpect(jsonPath("$.data[1].employeeName").value("John"));
    }
}
