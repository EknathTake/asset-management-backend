package com.idemia.assetmanagement.entity;

import com.idemia.assetmanagement.model.asset.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset_history")
public class AssetHistory {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "asset_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Asset asset;

    @JoinColumn(name = "empId")
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;

    private LocalDateTime dateOfAllocated;

    private LocalDateTime dateOfReturn;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    private String remark;

}
