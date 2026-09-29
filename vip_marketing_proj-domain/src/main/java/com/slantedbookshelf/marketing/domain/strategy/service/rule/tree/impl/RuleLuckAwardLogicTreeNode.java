package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.impl;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.slantedbookshelf.marketing.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 兜底奖励节点
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {

        // ruleValue  例如 "101:1,100"
        String[] split = ruleValue.split(Constants.COLON);
        if(split.length == 0){
            log.error("兜底奖品未配置");
        }
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue) // 1-100以内的积分
                        .build())
                .build();

    }
}
