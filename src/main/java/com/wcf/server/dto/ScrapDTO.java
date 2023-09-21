package com.wcf.server.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScrapDTO {
    private BigDecimal totalWeightKg;
    private Long totalPackage;

    public ScrapDTO(BigDecimal totalWeightKg, Long totalPackage) {
        this.totalWeightKg = totalWeightKg;
        this.totalPackage = totalPackage;
    }
}
