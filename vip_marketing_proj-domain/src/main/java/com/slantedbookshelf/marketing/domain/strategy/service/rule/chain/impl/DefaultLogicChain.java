package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl;

import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
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
    protected IStrategyDispatch strategyDispatch;

    @Override
    public DefaultChainFactory.StrategyAwardData logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardData.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }

}
