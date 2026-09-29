package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.impl;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 次数锁节点 （组合模式）
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    // mock一个用户抽奖次数
    private Long userRaffleCount = 10L;
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (NumberFormatException e) {
            throw new RuntimeException("次数锁异常");
        }

        // 次数满足，放行
        if(raffleCount <= userRaffleCount){
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW) // 拦截
                    .build();
        }

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER) // 拦截
                .build();
    }
}
