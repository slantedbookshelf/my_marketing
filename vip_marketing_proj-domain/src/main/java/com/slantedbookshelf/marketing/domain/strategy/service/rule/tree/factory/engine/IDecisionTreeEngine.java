package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;

/**
 * 规则树组合接口
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);
}
