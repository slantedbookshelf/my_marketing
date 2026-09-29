package com.slantedbookshelf.marketing.domain.activity.service.rule;

import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityCountEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivityEntity;
import com.slantedbookshelf.marketing.domain.activity.model.entity.ActivitySkuEntity;

public interface IActionChain extends IActionChainArmory{
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
