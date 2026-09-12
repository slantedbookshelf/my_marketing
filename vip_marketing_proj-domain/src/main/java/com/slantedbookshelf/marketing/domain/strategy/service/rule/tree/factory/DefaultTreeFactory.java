package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeVO;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 规则树工厂
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    // 返回可执行的规则树引擎接口
    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO){
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity{
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardData strategyAwardData;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardData{
        // 抽奖奖品id
        private Integer awardId;
        // 抽奖奖品规则
        private String awardRuleValue;
    }
}
