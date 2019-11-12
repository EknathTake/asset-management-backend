package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.Asset;
import com.idemia.assetmanagement.entity.Employee;
import com.idemia.assetmanagement.exception.ResourceNotFoundException;
import com.idemia.assetmanagement.repository.AssetRepository;
import com.idemia.assetmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Asset createAssetEntry(Asset asset) {

        Employee empEntity = employeeRepository.save(mapEmployeeToEntity(asset.getEmployee()));
        com.idemia.assetmanagement.entity.Asset entity = mapAssetToEntity(asset);
        entity.setEmployee(empEntity);
        return mapAssetToControllerResponse(assetRepository.save(entity));
    }

    private Asset mapAssetToControllerResponse(com.idemia.assetmanagement.entity.Asset asset) {
        return Asset.builder().assetTag(asset.getAssetTag())
                .dateAllocated(asset.getDateAllocated())
                .hostname(asset.getHostname())
                .model(asset.getModel())
                .ram(asset.getRam())
                .sNo(asset.getSNo())
                .serialNumber(asset.getSerialNumber())
                .status(asset.getStatus())
                .employee(mapEmployeeToControllerResponse(asset.getEmployee()))
                .build();
    }

    private com.idemia.assetmanagement.controller.response.Employee mapEmployeeToControllerResponse(Employee employee){
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

    private Employee mapEmployeeToEntity(com.idemia.assetmanagement.controller.response.Employee employee){
        return Employee.builder()
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

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll().stream().map(asset -> {
            return mapAssetToControllerResponse(asset);
        }).collect(Collectors.toList());
    }

    @Override
    public Asset getAssetById(Long id) {
        return mapAssetToControllerResponse(assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")));
    }

    @Override
    public List<Asset> getAssetByEmployeeId(Long employeeId) {
        return null;
    }

    private com.idemia.assetmanagement.entity.Asset mapAssetToEntity(Asset asset) {
        return com.idemia.assetmanagement.entity.Asset
                .builder()
                .assetTag(asset.getAssetTag())
                .dateAllocated(asset.getDateAllocated())
                .hostname(asset.getHostname())
                .model(asset.getModel())
                .ram(asset.getRam())
                .sNo(asset.getSNo())
                .serialNumber(asset.getSerialNumber())
                .status(asset.getStatus())
                .employee(mapEmployeeToEntity(asset.getEmployee()))
                .build();
    }

}
