package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.controller.response.Asset;

import java.util.List;

public interface AssetService {

    Asset createAssetEntry(Asset asset);

    List<Asset> getAllAssets();

    Asset getAssetById(Long id);

    List<Asset> getAssetByEmployeeId(Long employeeId);
}
