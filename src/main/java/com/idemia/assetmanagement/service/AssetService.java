package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.Asset;
import com.idemia.assetmanagement.model.asset.AssetSummary;
import com.idemia.assetmanagement.model.asset.Laptop;

import java.util.List;
import java.util.Set;

public interface AssetService {

    Asset createAssetEntry(Asset asset);

    List<Asset> getAllAssets();

    Asset getAssetById(Long id);

    List<Asset> getAssetByEmployeeId(Long employeeId);

    List<AssetSummary> getSummary();

    boolean removeAssetWithId(Long sNo);

    Asset updateAssetEntry(Asset asset);
}
