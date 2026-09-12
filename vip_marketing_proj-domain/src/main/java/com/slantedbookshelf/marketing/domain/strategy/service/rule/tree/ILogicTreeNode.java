package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree;

import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * 规则树接口
 */
public interface ILogicTreeNode {

    // 此时已经有了awardId，规则树的目的是再抽奖后根据库存进行兜底
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);
}
