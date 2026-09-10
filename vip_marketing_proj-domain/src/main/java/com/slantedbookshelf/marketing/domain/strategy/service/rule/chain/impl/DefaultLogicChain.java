package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl;

import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 兜底责任链节点
 */
@Slf4j
@Component("default")   // 为了bean的名字能和数据库匹配
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    protected IStrategyDispatch iStrategyDispatch;

    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer awardId = iStrategyDispatch.getRandomAwardId((strategyId));
        log.info("抽奖责任链-默认处理 userId:{} strategyId:{} ruleModel:{} awardId:{}",
                userId, strategyId, ruleModel(), awardId);
        return awardId;
    }

    @Override
    protected String ruleModel() {
        return "default";
    }
}
