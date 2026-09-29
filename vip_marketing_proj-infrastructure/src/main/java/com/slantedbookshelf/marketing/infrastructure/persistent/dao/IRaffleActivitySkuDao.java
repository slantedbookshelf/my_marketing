package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import com.slantedbookshelf.marketing.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRaffleActivitySkuDao {
    RaffleActivitySku queryActivitySku(Long sku) ;
}
