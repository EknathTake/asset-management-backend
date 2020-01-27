package com.idemia.assetmanagement.controller.response;

import com.idemia.assetmanagement.model.asset.AssetHistory;
import lombok.Data;

import java.util.List;

@Data
public class AssetHistoryResponse {


    private List<AssetHistory> data;
}
