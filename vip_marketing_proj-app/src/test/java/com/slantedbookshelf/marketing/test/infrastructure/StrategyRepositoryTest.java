package com.slantedbookshelf.marketing.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.slantedbookshelf.marketing.domain.strategy.model.valobj.RuleTreeVO;
import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.infrastructure.persistent.po.RuleTree;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;

    @Test
    public void test_queryRuleTreeVOByTreeId(){
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果：{}", JSON.toJSONString(ruleTreeVO));
    }




}
