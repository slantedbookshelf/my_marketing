package com.slantedbookshelf.marketing.domain.activity.service.rule;

public interface IActionChainArmory {
    IActionChain next();

    IActionChain appendNext(IActionChain next);

}


