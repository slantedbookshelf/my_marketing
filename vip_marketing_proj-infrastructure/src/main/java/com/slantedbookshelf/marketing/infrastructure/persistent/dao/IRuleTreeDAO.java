package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import com.slantedbookshelf.marketing.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRuleTreeDAO {
    RuleTree queryRuleTreeByTreeId(String treeId);
}
