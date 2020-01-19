package com.idemia.assetmanagement.service;

import com.idemia.assetmanagement.model.asset.Asset;
import com.idemia.assetmanagement.model.asset.AssetSummary;

import java.util.List;

public interface AssetService {

    Asset createAssetEntry(Asset asset);

    List<Asset> getAllAssets();

    List<AssetSummary> getSummary();

    boolean removeAssetWithId(Long sNo);

    Asset updateAssetEntry(Asset asset);
}
