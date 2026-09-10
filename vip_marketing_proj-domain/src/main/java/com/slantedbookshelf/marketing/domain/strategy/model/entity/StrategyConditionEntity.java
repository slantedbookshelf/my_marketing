package com.slantedbookshelf.marketing.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 策略条件实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyConditionEntity {
    /** 用户ID */
    private String userId;
    /** 策略ID */
    private Integer strategyId;
}
