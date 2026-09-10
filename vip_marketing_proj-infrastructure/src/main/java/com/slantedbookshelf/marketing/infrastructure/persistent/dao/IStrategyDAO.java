package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import com.slantedbookshelf.marketing.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDAO {
    List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);
}
