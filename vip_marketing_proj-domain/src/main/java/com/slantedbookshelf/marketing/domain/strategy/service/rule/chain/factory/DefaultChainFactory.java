package com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.factory;

import com.slantedbookshelf.marketing.domain.strategy.model.entity.StrategyEntity;
import com.slantedbookshelf.marketing.domain.strategy.repository.IStrategyRepository;
import com.slantedbookshelf.marketing.domain.strategy.service.rule.chain.ILogicChain;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainGroup;

    private final IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository){
        this.logicChainGroup = logicChainGroup;  // 通过此构造方法会自动把ILogicChain类型的bean装进map
        this.repository = repository;
    }

    // 按数据库的策略规则串联成一条责任链
    public ILogicChain openLogicChain(Long strategyId){
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        if(null == ruleModels || ruleModels.length == 0){
            return logicChainGroup.get("default");
        }

        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for(int i=1;i<ruleModels.length;i++){
            ILogicChain nextChain =logicChainGroup.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }
        current.appendNext(logicChainGroup.get("default"));

        return logicChain;
    }

}
