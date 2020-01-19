package com.idemia.assetmanagement.controller;

import com.idemia.assetmanagement.model.asset.AssetHistory;
import com.idemia.assetmanagement.service.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetHistoryController {

    @Autowired
    private AssetHistoryService assetHistoryService;


    @PostMapping("/history")
    public ResponseEntity<?> addAssetHistory(@RequestBody AssetHistory assetHistory) {

        AssetHistory assetHistoryResponse = assetHistoryService.createAssetHistory(assetHistory);
        if (null != assetHistoryResponse)
            return ResponseEntity.status(HttpStatus.CREATED).body(assetHistoryResponse);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/history")
    public List<AssetHistory> getAllAssets(){
        return assetHistoryService.getAllAssets();
    }
}
