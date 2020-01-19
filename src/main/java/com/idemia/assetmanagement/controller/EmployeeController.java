package com.idemia.assetmanagement.controller;

import com.idemia.assetmanagement.controller.response.Employee;
import com.idemia.assetmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @PostMapping("/employee")
    public ResponseEntity<Object> addEmployeesDetail(@RequestBody Employee employee) {
        Employee employeeResponse = employeeService.saveEmployeesDetail(employee);
        if (null != employeeResponse) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
