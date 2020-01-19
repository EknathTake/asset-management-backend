package com.idemia.assetmanagement.model.asset;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Asset {

    private Long sNo;

    private String assetTag;

    private String model;

    // (in GB)
    private String ram;

    private String serialNumber;

    private LocalDateTime dateOfPurchase;

    private boolean isUnderWarranty;

    private boolean isDamaged;

    private boolean isRepaired;

    public Long getsNo() {
        return sNo;
    }

    public void setsNo(Long sNo) {
        this.sNo = sNo;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDateTime getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDateTime dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public boolean isUnderWarranty() {
        return isUnderWarranty;
    }

    public void setUnderWarranty(boolean underWarranty) {
        isUnderWarranty = underWarranty;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void setRepaired(boolean repaired) {
        isRepaired = repaired;
    }
}
