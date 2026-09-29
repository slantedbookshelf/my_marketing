package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class Award {
    /** 自增ID */
    private Long id;
    /** 抽奖奖品ID - 内部流转使用 */ // 101
    private Integer awardId;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 */  // user_credit_random 随即积分
    private String awardKey;
    /** 奖品配置信息 */  // 1，100    意思就是1到100随机
    private String awardConfig;
    /** 奖品内容描述 */
    private String awardDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
