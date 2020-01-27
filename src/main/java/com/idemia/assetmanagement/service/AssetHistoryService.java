package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.AssetHistoryResponse;
import com.idemia.assetmanagement.controller.response.Employee;
import com.idemia.assetmanagement.model.asset.Asset;
import com.idemia.assetmanagement.model.asset.AssetHistory;
import com.idemia.assetmanagement.model.asset.AssetStatus;
import com.idemia.assetmanagement.repository.AssetHistoryRepository;
import com.idemia.assetmanagement.repository.AssetRepository;
import com.idemia.assetmanagement.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AssetHistoryService {

    @Autowired
    private AssetHistoryRepository assetHistoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public AssetHistory createAssetHistory(AssetHistory assetHistory) {

        com.idemia.assetmanagement.entity.AssetHistory assetHistoryEntity = mapAssetHistoryModelToEntity(assetHistory);

        return mapAssetHistoryEntityToModel(assetHistoryRepository.save(assetHistoryEntity));
    }

    public AssetHistory mapAssetHistoryEntityToModel(com.idemia.assetmanagement.entity.AssetHistory save) {

        com.idemia.assetmanagement.entity.Asset asset = assetRepository.findById(save.getAsset().getSNo()).orElse(null);

        return AssetHistory.builder()
                .id(save.getId())
                .dateOfAllocated(save.getDateOfAllocated())
                .dateOfReturn(save.getDateOfReturn())
                .asset(mapAssetEntityToModel(asset))
                .remark(save.getRemark())
                .status(save.getStatus())
                .employee(mapEmployeeEntityToModel(save.getEmployee()))
                .build();
    }

    private Employee mapEmployeeEntityToModel(com.idemia.assetmanagement.entity.Employee employee) {
        if (null != employee) {
            return Employee.builder()
                    .costCenter(employee.getCostCenter())
                    .empId(employee.getEmpId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .jobRole(employee.getJobRole())
                    .location(employee.getLocation())
                    .productLine(employee.getProductLine())
                    .technology(employee.getTechnology())
                    .build();
        }
        return null;
    }

    private Asset mapAssetEntityToModel(com.idemia.assetmanagement.entity.Asset asset) {
        return Asset.builder()
                .assetTag(asset.getAssetTag())
                .dateOfPurchase(asset.getDateOfPurchase())
                .isDamaged(asset.isDamaged())
                .isRepaired(asset.isRepaired())
                .isUnderWarranty(asset.isUnderWarranty())
                .model(asset.getModel())
                .ram(asset.getRam())
                .serialNumber(asset.getSerialNumber())
                .sNo(asset.getSNo())
                .build();
    }

    private com.idemia.assetmanagement.entity.AssetHistory mapAssetHistoryModelToEntity(AssetHistory assetHistory) {

        com.idemia.assetmanagement.entity.Asset asset = assetRepository.findById(assetHistory.getAsset().getsNo()).orElse(null);
        com.idemia.assetmanagement.entity.AssetHistory assetHistory1 = com.idemia.assetmanagement.entity.AssetHistory.builder()
                .id(assetHistory.getId())
                .asset(asset)
                .remark(assetHistory.getRemark())
                .status(assetHistory.getStatus())
                .dateOfAllocated(assetHistory.getDateOfAllocated())
                .dateOfReturn(assetHistory.getDateOfReturn())
                .build();
        if (null != assetHistory.getEmployee())
            assetHistory1.setEmployee(employeeRepository.findById(assetHistory.getEmployee().getEmpId()).orElse(null));
        if (null != assetHistory.getStatus() && !assetHistory.getStatus().equals(AssetStatus.ALLOCATED)) {
            //assetHistory1.setDateOfReturn(null);
            assetHistory1.setDateOfAllocated(null);
        }
        return assetHistory1;
    }

    private com.idemia.assetmanagement.entity.Employee mapEmployeeModelToEntity(Employee employee) {
        return com.idemia.assetmanagement.entity.Employee.builder()
                .costCenter(employee.getCostCenter())
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobRole(employee.getJobRole())
                .location(employee.getLocation())
                .productLine(employee.getProductLine())
                .technology(employee.getTechnology())
                .build();
    }

    private com.idemia.assetmanagement.entity.Asset mapAssetModelToEntity(Asset asset) {
        return com.idemia.assetmanagement.entity.Asset.builder()
                .assetTag(asset.getAssetTag())
                .dateOfPurchase(asset.getDateOfPurchase())
                .isDamaged(asset.isDamaged())
                .isRepaired(asset.isRepaired())
                .isUnderWarranty(asset.isUnderWarranty())
                .model(asset.getModel())
                .ram(asset.getRam())
                .serialNumber(asset.getSerialNumber())
                .sNo(asset.getsNo())
                .build();
    }

    public AssetHistoryResponse getAllAssetsHistory() {

        AssetHistoryResponse response = new AssetHistoryResponse();
        response.setData(assetHistoryRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(this::mapAssetHistoryEntityToModel)
                .collect(Collectors.toList()));
        return response;
    }
}


