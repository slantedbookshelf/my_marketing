package com.slantedbookshelf.marketing.domain.strategy.service.rule.filter;

import com.slantedbookshelf.marketing.domain.strategy.model.entity.RuleActionEntity;
import com.slantedbookshelf.marketing.domain.strategy.model.entity.RuleMatterEntity;

public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity>{
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
