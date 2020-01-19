package com.idemia.assetmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString(exclude = "employee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sNo;

    @Column(unique = true)
    private String assetTag;

    private String model;

    // (in GB)
    private String ram;

    private String serialNumber;

    private LocalDateTime dateOfPurchase;

    private boolean isUnderWarranty;

    private boolean isDamaged;

    private boolean isRepaired;

}
