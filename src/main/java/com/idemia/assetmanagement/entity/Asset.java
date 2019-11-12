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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_employee")
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
