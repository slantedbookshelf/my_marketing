package com.slantedbookshelf.marketing.infrastructure.persistent.dao;

import com.slantedbookshelf.marketing.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IRuleTreeNodeDAO {
    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);
}
