package com.idemia.assetmanagement.repository;

import com.idemia.assetmanagement.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AssetRepository extends JpaRepository<Asset, Long> {
//
//    @Modifying
//    @Query()
//    int updateAssetDetails();
}
