package com.idemia.assetmanagement.model.asset;

import com.idemia.assetmanagement.controller.response.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AssetHistory {

    private Long id;

    private Asset asset;

    private Employee employee;

    private LocalDateTime dateOfAllocated;

    private LocalDateTime dateOfReturn;

    private AssetStatus status;

    private String remark;

}
