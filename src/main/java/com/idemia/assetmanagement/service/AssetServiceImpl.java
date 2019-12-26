package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.Asset;
import com.idemia.assetmanagement.entity.Employee;
import com.idemia.assetmanagement.exception.ResourceNotFoundException;
import com.idemia.assetmanagement.model.asset.AssetSummary;
import com.idemia.assetmanagement.model.asset.Laptop;
import com.idemia.assetmanagement.repository.AssetRepository;
import com.idemia.assetmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

    //Asset Models
    public static final String MAC_BOOK = "MacBook";
    public static final String WINDOWS = "Lenovo";

    //RAM
    public static final String RAM16 = "16";
    public static final String RAM8 = "8";
    public static final String RAM4 = "4";

    //Asset Status
    public static final String ALLOCATED = "Allocated";
    public static final String INVENTORY = "Inventory";
    public static final String INVENTORY_NOT_WORKING = "Inventory - not working";
    public static final String DISCARDED = "Discarded";

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

    private com.idemia.assetmanagement.controller.response.Employee mapEmployeeToControllerResponse(Employee employee) {
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

    private Employee mapEmployeeToEntity(com.idemia.assetmanagement.controller.response.Employee employee) {
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

    @Override
    public List<AssetSummary> getSummary() {

        List<Asset> assets = getAllAssets();
        List<String> assetStatus = Arrays.asList(ALLOCATED, INVENTORY_NOT_WORKING, INVENTORY, DISCARDED);

        List<AssetSummary> row9 = filterAssetsBtStatus(assets, MAC_BOOK, assetStatus);
        List<AssetSummary> row5 = filterAssetsBtStatus(assets, WINDOWS, assetStatus);

        row9.addAll(row5);
        return row9.stream().sorted(Comparator.comparing(o -> o.getAssetDetails().getStatus())).collect(Collectors.toList());
    }

    private List<AssetSummary> filterAssetsBtStatus(List<Asset> assets, String model, List<String> statusList) {
        List<AssetSummary> assetSummaryList = new ArrayList<>();
        for (String status: statusList) {
            AssetSummary assetSummary = new AssetSummary();
            long count16 = 0, count8 = 0, count4 = 0;

            Laptop laptop = null;
            for (Asset asset : assets) {
                 laptop = new Laptop();
                if (asset.getModel().contains(model) && asset.getStatus().equals(status)) {
                    if (asset.getRam().equals(RAM16)) {
                        count16 += 1;
                    }
                    if (asset.getRam().equals(RAM8)) {
                        count8 += 1;

                    }
                    if (asset.getRam().equals(RAM4)) {
                        count4 += 1;
                    }
                }
                laptop.setRam16GB(count16);
                laptop.setRam8GB(count8);
                laptop.setRam4GB(count4);
                laptop.setModel(model);
                laptop.setTotal(count4+count8+count16);
                laptop.setStatus(status);
            }

            //assetSummary.setStatus(status);
            assetSummary.setAssetDetails(laptop);
            assetSummaryList.add(assetSummary);
        }
        return assetSummaryList;
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
