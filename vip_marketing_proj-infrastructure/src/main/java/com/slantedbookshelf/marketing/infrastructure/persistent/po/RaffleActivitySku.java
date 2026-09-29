package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

@Data
public class RaffleActivitySku {
    private Long sku;
    private Long activityId;
    private Long activityCountId;
    private Integer stockCount;
    private Integer stockCountSurplus;
}
