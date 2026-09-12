package com.slantedbookshelf.marketing.domain.strategy.service.raffle;

import com.slantedbookshelf.marketing.domain.strategy.model.entity.RaffleFactorEntity;
import com.slantedbookshelf.marketing.domain.strategy.model.entity.RuleActionEntity;
import com.slantedbookshelf.marketing.domain.strategy.model.entity.RuleMatterEntity;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.AbstractRaffleStrategy;
import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.ILogicChain;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.filter.ILogicFilter;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    @Resource
    private DefaultLogicFactory logicFactory;

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    @Override
    public DefaultChainFactory.StrategyAwardData raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }


    @Override
    public DefaultTreeFactory.StrategyAwardData raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardData.builder().awardId(awardId).build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }





}
