package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.slantedbookshelf.marketing.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {

    //@DBRouterStrategy(splitTable = true) 作用是执行 MyBaits 操作的时候，对 SQL 语句进行动态变更。
    //@DBRouter 指定对哪个SQL的操作进行路由。默认路由字段就是 userId 你可以配置也可以不配置。

    @DBRouter(key = "userId")
    void insert(RaffleActivityOrder raffleActivityOrder);

    @DBRouter
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);
}
