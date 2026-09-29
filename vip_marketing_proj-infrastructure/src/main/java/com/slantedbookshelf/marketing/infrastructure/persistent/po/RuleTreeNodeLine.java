package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class RuleTreeNodeLine {
    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;   // tree_lock_1
    /** 规则Key节点 From */
    private String ruleNodeFrom;   // rule_lock
    /** 规则Key节点 To */
    private String ruleNodeTo;    // rule_stock
    /** 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围] */
    private String ruleLimitType;   // EQUAL
    /** 限定值（到下个节点） */
    private String ruleLimitValue;   // ALLOW
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
