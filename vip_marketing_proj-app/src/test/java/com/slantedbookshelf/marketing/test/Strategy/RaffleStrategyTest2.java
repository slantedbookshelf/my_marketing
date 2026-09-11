package com.slantedbookshelf.marketing.test.Strategy;

import com.alibaba.fastjson.JSON;
import com.slantedbookshelf.marketing.domain.strategy.model.entity.RaffleAwardEntity;
import com.slantedbookshelf.marketing.domain.strategy.model.entity.RaffleFactorEntity;
import com.slantedbookshelf.marketing.domain.strategy.service.IRaffleStrategy;
import com.slantedbookshelf.marketing.domain.strategy.service.armory.StrategyArmory;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class RaffleStrategyTest2 {

    @Resource
    private StrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;

    @Resource
    private IRaffleStrategy raffleStrategy;


    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 40500L);
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 0L);
    }

    /**
     * 次数错校验，抽奖n次后解锁。100003 策略，你可以通过调整 @Before 的 setUp 方法中个人抽奖次数来验证。比如最开始设置0，之后设置10
     * ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
     */
    @Test
    public void test_raffle_center_rule_lock(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("xiaofuge")
                .strategyId(100001L)
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }

}
