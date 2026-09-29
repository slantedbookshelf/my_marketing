package com.slantedbookshelf.marketing.domain.activity.service;

import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityCountEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivitySkuEntity;
import com.slantedbookshelf.marketing.domain.activity.repository.IActivityRepository;
import com.slantedbookshelf.marketing.domain.activity.service.rule.factory.DefaultActivityChainFactory;

public class RaffleActivitySupport {
    protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    public RaffleActivitySupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }


}
