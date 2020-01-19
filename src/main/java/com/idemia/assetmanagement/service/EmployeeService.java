package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.Employee;
import com.idemia.assetmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> mapEmployeeToControllerResponse(employee))
                .collect(Collectors.toList());
    }

    private com.idemia.assetmanagement.controller.response.Employee mapEmployeeToControllerResponse(com.idemia.assetmanagement.entity.Employee employee) {
        return com.idemia.assetmanagement.controller.response.Employee.builder()
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobRole(employee.getJobRole())
                .technology(employee.getTechnology())
                .costCenter(employee.getCostCenter())
                .productLine(employee.getProductLine())
                .location(employee.getLocation())
                .build();
    }

    private com.idemia.assetmanagement.entity.Employee mapEmployeeToEntity(com.idemia.assetmanagement.controller.response.Employee employee) {
        return com.idemia.assetmanagement.entity.Employee.builder()
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobRole(employee.getJobRole())
                .technology(employee.getTechnology())
                .costCenter(employee.getCostCenter())
                .productLine(employee.getProductLine())
                .location(employee.getLocation())
                .build();
    }

    public Employee saveEmployeesDetail(Employee employee) {

        return mapEmployeeToControllerResponse(employeeRepository.save(mapEmployeeToEntity(employee)));
    }
}
