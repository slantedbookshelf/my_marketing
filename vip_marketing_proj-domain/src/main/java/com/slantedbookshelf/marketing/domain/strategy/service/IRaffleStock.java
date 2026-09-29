package com.slantedbookshelf.marketing.domain.strategy.service;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * 抽奖坤村相关服务
 */
public interface IRaffleStock {
    /**
     * 获取奖品库存消耗队列
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 更新奖品库存消耗记录
     */
    void updateStrategyAwardStock(Long strageId, Integer awardId);
}
