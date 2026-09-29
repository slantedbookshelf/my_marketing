package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class RuleTreeNode {
    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;  // tree_lock_1
    /** 规则Key */
    private String ruleKey;  // rule_luck_award
    /** 规则描述 */
    private String ruleDesc;  // 兜底随机积分
    /** 规则比值 */
    private String ruleValue;   // 101:1,100
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
