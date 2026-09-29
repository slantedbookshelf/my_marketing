package com.slantedbookshelf.marketing.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.slantedbookshelf.marketing.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import com.slantedbookshelf.marketing.infrastructure.persistent.po.RaffleActivityOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityDaoTest {

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Test
    public void test_insert() {
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId("SlantedBookshelf");
        raffleActivityOrder.setActivityId(100301L);
        raffleActivityOrder.setActivityName("测试活动");
        raffleActivityOrder.setStrategyId(100006L);
        raffleActivityOrder.setOrderId(RandomStringUtils.randomNumeric(12));
        raffleActivityOrder.setOrderTime(new Date());
        raffleActivityOrder.setState("not_used");
        // 插入数据
        raffleActivityOrderDao.insert(raffleActivityOrder);
    }

    @Test
    public void test_queryRaffleActivityOrderByUserId() {
        String userId = "SlantedBookshelf";
        List<RaffleActivityOrder> raffleActivityOrders = raffleActivityOrderDao.queryRaffleActivityOrderByUserId(userId);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrders));
    }

}
