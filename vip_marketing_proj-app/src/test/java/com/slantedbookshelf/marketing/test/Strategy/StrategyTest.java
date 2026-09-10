package com.slantedbookshelf.marketing.test.Strategy;

import com.slantedbookshelf.marketing.domain.strategy.service.armory.StrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class StrategyTest {


    @Autowired
    private StrategyArmory strategyArmory;
    /**
     * 1.策略装配
     * 策略ID；100001L、100002L 装配的时候创建策略表写入到 Redis Map 中
     */
    @Test
    public void test_strategyArmory() {
        boolean success = strategyArmory.assembleLotteryStrategy(100002L);
        log.info("测试结果：{}", success);
    }

    /**
     * 2.执行抽奖
     * 从装配的策略中随机获取奖品ID值
     */
    @Test
    public void test_getAssembleRandomVal() {
        log.info("测试结果：{} - 奖品ID值", strategyArmory.getRandomAwardId(100002L));
    }

}
