package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class Strategy {
    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;   // 100001
    /** 抽奖策略描述 */
    private String strategyDesc;    // 抽奖策略
    /** 抽奖规则模型 */
    private String ruleModels;   // rule_blacklist,rule_weight
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
