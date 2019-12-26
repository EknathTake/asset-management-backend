package com.idemia.assetmanagement.controller;

import com.idemia.assetmanagement.controller.response.Asset;
import com.idemia.assetmanagement.model.asset.AssetSummary;
import com.idemia.assetmanagement.service.AssetService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public DataTableResponse getAllAsset() {
        DataTableResponse dtr = new DataTableResponse();
        dtr.setData(assetService.getAllAssets());
        return dtr;
    }

    @PostMapping
    public ResponseEntity<Asset> createAssetEntry(@RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.createAssetEntry(asset));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<AssetSummary>> getSummary() {
        return ResponseEntity.ok(assetService.getSummary());
    }
}

@Data
class DataTableResponse {
    private List<Asset> data;
}
