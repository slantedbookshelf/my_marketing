package com.slantedbookshelf.marketing.domain.activity.repository;

import com.slantedbookshelf.marketing.domain.activity.model.aggregate.CreateOrderAggregate;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityCountEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivitySkuEntity;

public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}
