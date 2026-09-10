package com.slantedbookshelf.marketing.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.slantedbookshelf.marketing.infrastructure.persistent.dao.IAwardDAO;
import com.slantedbookshelf.marketing.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
public class AwardDAOTest {
    @Resource
    private IAwardDAO awardDao;

    @Test
    public void test_queryAwardList() {
        List<Award> awards = awardDao.queryAwardList();
        log.info("测试结果：{}", JSON.toJSONString(awards));
    }
}
