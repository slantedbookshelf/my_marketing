package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl;

import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.slantedbookshelf.marketing.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 黑名单方法
 */
@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单开始 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId,ruleModel());

        String[] splitRuleValue = ruleValue.split(Constants.COLON);  // :
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 过滤黑名单
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);   // ,
        for (String userBlackId : userBlackIds) {
            if(userId.equals(userBlackId)){
                log.info("抽奖责任链-黑名单接管 userId:{} strategyId:{} ruleModel:{} awardId:{}",
                        userId, strategyId, ruleModel(), awardId);
                return awardId;
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());

        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return "rule_blacklist";
    }
}
