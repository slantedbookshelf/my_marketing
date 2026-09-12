package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain;

import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * 责任链接口
 */
public interface ILogicChain extends ILogicChainArmory{

    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品对象
     */
    DefaultChainFactory.StrategyAwardData logic(String userId, Long strategyId);

}
