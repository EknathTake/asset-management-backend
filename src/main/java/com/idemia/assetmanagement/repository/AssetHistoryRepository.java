package com.idemia.assetmanagement.repository;

import com.idemia.assetmanagement.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
}
