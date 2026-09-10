package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import com.slantedbookshelf.marketing.infrastructure.persistent.po.RuleTree;

public interface IRuleTreeDAO {
    RuleTree queryRuleTreeByTreeId(String treeId);
}
