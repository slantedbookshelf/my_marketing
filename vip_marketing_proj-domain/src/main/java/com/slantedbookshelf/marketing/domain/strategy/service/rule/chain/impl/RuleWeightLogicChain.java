package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl;

import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.slantedbookshelf.marketing.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Resource
    private IStrategyDispatch strategyDispatch;

    public Long userScore = 0L;

    /**
     * 权重责任链过滤：
     * 1. 权重规则格式：4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式：判断哪个范围符合用户的特定抽奖范围
     * @param userId  用户id
     * @param strategyId   策略id
     * @return
     */
    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-权重规则开始 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId,ruleModel());

        // 1. 根据用户id查询用户抽奖消耗的积分值
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if(null == analyticalValueGroup || analyticalValueGroup.isEmpty()){
            return null;
        }

        // 2. 转换Keys值，并默认排序
        List<Long> anaylicalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(anaylicalSortedKeys);

        // 3. 找出最小符合的值
        Long nextValue = anaylicalSortedKeys.stream()
                .sorted(Comparator.reverseOrder())  // 带这个参数就是降序
                .filter(anaylitcalSortedKeyValue -> userScore >= anaylitcalSortedKeyValue)
                .findFirst()
                .orElse(null);  // 兜底，一个都不满足时返回null

        if(null != nextValue){
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
            log.info("抽奖责任链-权重接管 userId:{} strategyId:{} ruleModel:{}, awardId:{}",
                    userId, strategyId, ruleModel(),awardId);
            return awardId;
        }

        // 过滤其他责任链
        log.info("抽奖责任链-权重放行 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());

        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return "rule_weight";
    }

    // 将字符串变成哈希表
    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueKey == null || ruleValueKey.isEmpty()) {
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);  // :
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValueKey);
            }
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }

}
