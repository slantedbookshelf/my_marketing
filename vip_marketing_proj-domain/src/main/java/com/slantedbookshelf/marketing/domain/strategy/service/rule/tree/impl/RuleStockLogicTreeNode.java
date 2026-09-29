package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.impl;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 库存节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Resource
    private final IStrategyDispatch iStrategyDispatch;
    @Resource
    private IStrategyRepository strategyRepository;


    public RuleStockLogicTreeNode(IStrategyDispatch iStrategyDispatch) {
        this.iStrategyDispatch = iStrategyDispatch;
    }

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("扣减库存：userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        Boolean status = iStrategyDispatch.subtractionAwardStock(strategyId, awardId);
        if(status){
            // 发一个异步的队列消息(redis的缓存操作)
            strategyRepository.awardStockConsumeSendQueue(
                    StrategyAwardStockKeyVO.builder()
                            .strategyId(strategyId)
                            .awardId(awardId)
                            .build());

            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER) // 拦截
                    .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder()
                            .awardId(awardId)
                            .awardRuleValue(ruleValue)
                            .build())
                    .build();
        }
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW) // 放行
                .build();
    }
}
