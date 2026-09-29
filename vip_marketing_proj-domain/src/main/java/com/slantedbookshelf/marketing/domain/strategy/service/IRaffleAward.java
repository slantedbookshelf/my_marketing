package com.slantedbookshelf.marketing.domain.strategy.service;

import com.slantedbookshelf.marketing.domain.strategy.model.entity.StrategyAwardEntity;
import com.slantedbookshelf.marketing.types.common.Constants;

import java.util.List;

public interface IRaffleAward {

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}

