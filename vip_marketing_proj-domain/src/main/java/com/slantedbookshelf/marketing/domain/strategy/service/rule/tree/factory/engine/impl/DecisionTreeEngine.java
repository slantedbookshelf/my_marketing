package com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.impl;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeVO;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 规则树引擎
 */
@Slf4j
public class DecisionTreeEngine implements IDecisionTreeEngine {
    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;
    private final RuleTreeVO ruleTreeVO;

    public DecisionTreeEngine(Map<String, ILogicTreeNode> logicTreeNodeGroup, RuleTreeVO ruleTreeVO) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
        this.ruleTreeVO = ruleTreeVO;
    }

    // 从规则树根节点开始，一路执行节点规则，根据每个节点返回的结果决定走到哪个下一个节点，
    // 直到没有下一个节点为止，最后返回规则树给出的奖品结果
    @Override
    public DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId) {
        DefaultTreeFactory.StrategyAwardData strategyAwardData = null;

        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();

        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while(null != nextNode){
            ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());

            // 决策节点计算
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId);
            RuleLogicCheckTypeVO ruleLogicCheckType = logicEntity.getRuleLogicCheckType();
            strategyAwardData = logicEntity.getStrategyAwardData();

            log.info("决策树引擎【{}】treeId:{} node:{} code:{}", ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode, ruleLogicCheckType.getCode());

            // 获取下个节点
            nextNode = nextNode(ruleLogicCheckType.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);
        }
        return strategyAwardData;
    }

    private String nextNode(String matterValue, List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList){
        if(null == ruleTreeNodeLineVOList || ruleTreeNodeLineVOList.isEmpty()){
            return null;
        }
        for(RuleTreeNodeLineVO nodeLine : ruleTreeNodeLineVOList){
            if(decisionLogic(matterValue, nodeLine)){
                return nodeLine.getRuleNodeTo();
            }
        }
        throw new RuntimeException("规则树引擎，nextNode 计算失败，未找到可执行节点");
    }

    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO nodeLine){
        switch (nodeLine.getRuleLimitType()){
            case EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue().getCode());
            // 暂时不需要实现
            case GT:
            case LT:
            case GE:
            case LE:
            default:
                return false;
        }
    }
}
