package com.slantedbookshelf.marketing.domain.activity.model.entity;

import lombok.Data;

@Data
public class SkuRechargeEntity {
    private String userId;
    private Long sku;
    private String outBusinessNo;
}
