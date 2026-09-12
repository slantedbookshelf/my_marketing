package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl;

import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.armory.IStrategyDispatch;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
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
     * 权重责任链过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     */
    @Override
    public DefaultChainFactory.StrategyAwardData logic(String userId, Long strategyId) {
        log.info("抽奖责任链-权重开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());

        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());

        // 1. 解析权重规则值 4000:102,103,104,105 拆解为；4000 -> 4000:102,103,104,105 便于比对判断
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            log.warn("抽奖责任链-权重告警【策略配置权重，但ruleValue未配置相应值】 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
            return next().logic(userId, strategyId);
        }

        // 2. 转换Keys值，并默认排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        // 3. 找出最小符合的值，也就是【4500 积分，能找到 4000:102,103,104,105】、【5000 积分，能找到 5000:102,103,104,105,106,107】
        /* 找到最后一个符合的值[如用户传了一个 5900 应该返回正确结果为 5000]，如果使用 Lambda findFirst 需要注意使用 sorted 反转结果
         *   Long nextValue = null;
         *         for (Long analyticalSortedKeyValue : analyticalSortedKeys) {
         *             if (userScore >= analyticalSortedKeyValue){
         *                 nextValue = analyticalSortedKeyValue;
         *             }
         *         }
         * 星球伙伴 @慢慢来 ID 6267 提供
         * Long nextValue = analyticalSortedKeys.stream()
         *      .filter(key -> userScore >= key)
         *      .max(Comparator.naturalOrder())
         *      .orElse(null);
         */
        Long nextValue = analyticalSortedKeys.stream()
                .sorted(Comparator.reverseOrder())
                .filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue)
                .findFirst()
                .orElse(null);

        // 4. 权重抽奖
        if (null != nextValue) {
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
            log.info("抽奖责任链-权重接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardData.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }

        // 5. 过滤其他责任链
        log.info("抽奖责任链-权重放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
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
