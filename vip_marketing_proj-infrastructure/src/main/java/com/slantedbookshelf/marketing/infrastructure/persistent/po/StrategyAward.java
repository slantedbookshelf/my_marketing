package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StrategyAward {
    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;  // 100001
    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;  // 101
    /** 抽奖奖品标题 */
    private String awardTitle;    // 随机积分
    /** 抽奖奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存总量 */
    private Integer awardCount;   // 80000
    /** 奖品库存剩余 */
    private Integer awardCountSurplus;   // 80000
    /** 奖品中奖概率 */
    private BigDecimal awardRate;   // 0.3000
    /** 规则模型，rule配置的模型同步到此表，便于使用 */
    private String ruleModels;     // tree_luck_award
    /** 排序 */
    private Integer sort;    // 1
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
}
