package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.model.asset.Asset;
import com.idemia.assetmanagement.entity.Employee;
import com.idemia.assetmanagement.exception.ResourceNotFoundException;
import com.idemia.assetmanagement.model.asset.AssetHistory;
import com.idemia.assetmanagement.model.asset.AssetSummary;
import com.idemia.assetmanagement.model.asset.Laptop;
import com.idemia.assetmanagement.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

    //Asset Models
    public static final String MAC_BOOK = "MacBook";
    public static final String WINDOWS = "Windows";

    //RAM
    public static final String RAM16 = "16";
    public static final String RAM8 = "8";
    public static final String RAM4 = "4";

    //Asset Status
    public static final String ALLOCATED = "ALLOCATED";
    public static final String INVENTORY = "INVENTORY";
    public static final String INVENTORY_NOT_WORKING = "INVENTORY_NOT_WORKING";
    public static final String DISCARDED = "DISCARDED";

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetHistoryService assetHistoryService;

    private static String getStatus(AssetSummary o) {
        return o.getAssetDetails().getStatus();
    }

    @Override
    @Transactional
    public Asset createAssetEntry(Asset asset) {

        return mapAssetToControllerResponse(assetRepository.save(mapAssetToEntity(asset)));
    }

    private Asset mapAssetToControllerResponse(com.idemia.assetmanagement.entity.Asset asset) {
        return Asset.builder().assetTag(asset.getAssetTag())
                .model(asset.getModel())
                .ram(asset.getRam())
                .sNo(asset.getSNo())
                .serialNumber(asset.getSerialNumber())
                .dateOfPurchase(asset.getDateOfPurchase())
                .isDamaged(asset.isDamaged())
                .isRepaired(asset.isRepaired())
                .isUnderWarranty(asset.isUnderWarranty())
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
/*

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
*/

    @Override
    @Transactional
    public List<Asset> getAllAssets() {
        return assetRepository.findAll()
                .stream().map(asset -> mapAssetToControllerResponse(asset))
                .sorted(Comparator
                        .comparing(Asset::getsNo)
                        .reversed()).
                        collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AssetSummary> getSummary() {

        //List<Asset> assets = getAllAssets();
        List<AssetHistory> assetHistories = assetHistoryService.getAllAssetsHistory().getData();
                //.stream()
               // .filter(Objects::nonNull)
                //.map(assetHistory -> assetHistoryService.(assetHistory))
               // .collect(Collectors.toList());
        List<String> assetStatus = Arrays.asList(ALLOCATED, INVENTORY_NOT_WORKING, INVENTORY, DISCARDED);

        List<AssetSummary> row9 = filterAssetsBtStatus(assetHistories, MAC_BOOK, assetStatus);
        List<AssetSummary> row5 = filterAssetsBtStatus(assetHistories, WINDOWS, assetStatus);

        row9.addAll(row5);
        return row9.stream()
                .sorted(Comparator.comparing(AssetServiceImpl::getStatus))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean removeAssetWithId(Long sNo) {
        if (assetRepository.findById(sNo).isPresent()) {
            assetRepository.deleteById(sNo);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Asset updateAssetEntry(Asset asset) {
        return assetRepository.findById(asset.getsNo())
                .map(newAssetDetails -> {
                    newAssetDetails.setAssetTag(asset.getAssetTag());
                    newAssetDetails.setModel(asset.getModel());
                    newAssetDetails.setRam(asset.getRam());
                    newAssetDetails.setSNo(asset.getsNo());
                    newAssetDetails.setSerialNumber(asset.getSerialNumber());
                    newAssetDetails.setDateOfPurchase(asset.getDateOfPurchase());
                    newAssetDetails.setDamaged(asset.isDamaged());
                    newAssetDetails.setUnderWarranty(asset.isUnderWarranty());
                    newAssetDetails.setRepaired(asset.isRepaired());
                    return mapAssetToControllerResponse(assetRepository.save(newAssetDetails));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + asset));
    }

    private List<AssetSummary> filterAssetsBtStatus(List<AssetHistory> assets, String model, List<String> statusList) {
        List<AssetSummary> assetSummaryList = new ArrayList<>();
        for (String status : statusList) {
            AssetSummary assetSummary = new AssetSummary();
            long count16 = 0, count8 = 0, count4 = 0;

            Laptop laptop = null;
            for (AssetHistory asset : assets) {
                laptop = new Laptop();
                if (asset.getAsset().getModel().toLowerCase().contains(model.toLowerCase()) && asset.getStatus().name().toLowerCase().equals(status.toLowerCase())) {
                //if (asset.getAsset().getModel().toLowerCase().contains(model.toLowerCase())) {
                    if (asset.getAsset().getRam().equals(RAM16)) {
                        count16 += 1;
                    }
                    if (asset.getAsset().getRam().equals(RAM8)) {
                        count8 += 1;

                    }
                    if (asset.getAsset().getRam().equals(RAM4)) {
                        count4 += 1;
                    }
                }
                laptop.setRam16GB(count16);
                laptop.setRam8GB(count8);
                laptop.setRam4GB(count4);
                laptop.setModel(model);
                laptop.setTotal(count4 + count8 + count16);
                laptop.setStatus(status);
            }

//            assetSummary.getAssetDetails().setStatus(status);
            assetSummary.setAssetDetails(laptop);
            assetSummaryList.add(assetSummary);
        }
        return assetSummaryList;
    }

    private com.idemia.assetmanagement.entity.Asset mapAssetToEntity(Asset asset) {
        return com.idemia.assetmanagement.entity.Asset
                .builder()
                .assetTag(asset.getAssetTag())
                .model(asset.getModel())
                .ram(asset.getRam())
                .sNo(asset.getsNo())
                .serialNumber(asset.getSerialNumber())
                .dateOfPurchase(asset.getDateOfPurchase())
                .isDamaged(asset.isDamaged())
                .isRepaired(asset.isRepaired())
                .isUnderWarranty(asset.isUnderWarranty())
                .build();
    }

}
