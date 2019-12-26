package com.idemia.assetmanagement.model.asset;

import lombok.Data;

@Data
public class Laptop {
    private String status;

    private String model;

    private Long ram16GB;

    private Long ram8GB;

    private Long ram4GB;

    private Long total;
}
