package com.idemia.assetmanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    private Long sNo;

    private Employee employee;

    private String model;

    // (in GB)
    private String ram;

    private String serialNumber;

    private String assetTag;

    private LocalDateTime dateAllocated;

    private String hostname;

    private String status;
}
