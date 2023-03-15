package com.app.account.dto;

import lombok.Data;

@Data
public class PromotionStatus {
    private Boolean isEligibleForPromotion;
    private String cause;
}
