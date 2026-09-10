# VIP Marketing Project 项目文档

## 1. 项目定位

`vip_marketing_proj` 是一个基于 Spring Boot + MyBatis + Redis 的营销抽奖项目。当前工程重点围绕“抽奖策略装配”和“随机奖品分发”展开，支持根据策略配置、奖品概率、权重规则生成抽奖概率表，并将装配结果缓存到 Redis，供后续抽奖调用快速读取。

项目采用 DDD 风格的多模块结构，将领域逻辑、基础设施、应用启动、接口模型等职责拆分到不同 Maven 模块中。

## 2. 技术栈

- Java 8 编译目标，当前本地测试使用 JDK 17 运行。
- Spring Boot 2.7.12。
- MyBatis Spring Boot Starter 2.1.4。
- MySQL 8 驱动。
- Redisson 3.26.0，用于 Redis 访问。
- Lombok，用于简化实体、配置类代码。
- JUnit 4 / JUnit 5 测试并存，当前 `StrategyTest2` 使用 JUnit 4 风格。

## 3. 模块结构

### vip_marketing_proj-app

应用启动模块，包含：

- `Application`：Spring Boot 启动类。
- `application.yml` / `application-dev.yml`：应用、MySQL、MyBatis、Redis、线程池配置。
- `RedisClientConfig`：RedissonClient 初始化配置。
- `ThreadPoolConfig`：线程池配置。
- `src/test`：当前主要单元测试入口。

### vip_marketing_proj-domain

领域模块，包含抽奖策略核心逻辑：

- `IStrategyArmory`：策略装配接口。
- `IStrategyDispatch`：抽奖调度接口。
- `StrategyArmory`：基础策略装配与随机奖品获取。
- `StrategyArmoryDispatch`：增强版装配与调度，支持默认概率表、权重概率表、奖品库存缓存。
- `IStrategyRepository`：领域仓储接口，由基础设施层实现。
- `StrategyEntity`、`StrategyAwardEntity`、`StrategyRuleEntity`：领域实体。

### vip_marketing_proj-infrastructure

基础设施模块，负责数据库和 Redis 访问：

- `StrategyRepository`：实现领域仓储接口，完成 PO 到领域实体的转换，并封装 Redis 缓存读写。
- `persistent.dao`：MyBatis Mapper 接口。
- `persistent.po`：数据库持久化对象。
- `RedisService` / `IRedisService`：Redis 通用操作封装。

### vip_marketing_proj-api

接口模型模块，目前主要包含统一响应对象 `Response<T>`，DTO 目录仍处于占位状态。

### vip_marketing_proj-trigger

触发器模块，目前 `http`、`job`、`listener` 目录以占位 `package-info` 为主，尚未形成具体接口或任务入口。

### vip_marketing_proj-types

通用类型模块，当前包含：

- `Constants`：Redis key、分隔符等常量。
- `ResponseCode`：响应码枚举。
- `AppException`：应用异常类型。

## 4. 当前已实现的核心功能

### 4.1 策略奖品查询

系统通过 MyBatis 从 `strategy_award` 表查询指定策略下的奖品列表，返回持久化对象 `StrategyAward`。基础设施层 `StrategyRepository` 会将其转换成领域对象 `StrategyAwardEntity`，避免领域层直接依赖数据库 PO。

相关入口：

- `IStrategyAwardDAO#queryStrategyAwardListByStrategyId`
- `StrategyRepository#queryStrategyAwardList`

### 4.2 默认概率表装配

策略装配会读取策略下所有奖品概率，根据最小概率计算概率范围，并生成一个“概率索引 -> 奖品ID”的查找表。

例如奖品概率越高，奖品 ID 在概率表中出现次数越多。抽奖时只需要生成一个随机下标，再从 Redis Map 中取出对应奖品 ID。

缓存 key：

- 概率范围：`big_market_strategy_rate_range_key_{strategyId}`
- 概率表：`big_market_strategy_rate_table_key_{strategyId}`

相关入口：

- `StrategyArmory#assembleLotteryStrategy`
- `StrategyArmoryDispatch#assembleLotteryStrategy`

### 4.3 权重概率表装配

`StrategyArmoryDispatch` 支持读取策略规则 `rule_weight`，按不同权重值生成独立概率表。

示例规则：

```text
4000:102,103,104,105
5000:102,103,104,105,106,107
6000:102,103,104,105,106,107,108,109
```

系统会分别生成如下 Redis key：

```text
big_market_strategy_rate_range_key_100001_4000:102,103,104,105
big_market_strategy_rate_table_key_100001_4000:102,103,104,105
```

调用 `getRandomAwardId(100001L, "4000:102,103,104,105")` 时，会根据组合 key 读取对应权重范围内的概率表。

### 4.4 随机奖品获取

抽奖调用流程：

1. 根据策略 ID 或权重组合 key 读取 Redis 中的概率范围。
2. 使用 `SecureRandom#nextInt(rateRange)` 生成随机下标。
3. 从 Redis Map 中读取该下标对应的奖品 ID。

相关入口：

- `StrategyArmory#getRandomAwardId`
- `StrategyArmoryDispatch#getRandomAwardId(Long strategyId)`
- `StrategyArmoryDispatch#getRandomAwardId(Long strategyId, String ruleWeightValue)`

### 4.5 奖品库存缓存

`StrategyArmoryDispatch` 在装配策略时会将奖品库存写入 Redis 原子计数器。

缓存 key：

```text
strategy_award_count_key_{strategyId}_{awardId}
```

当前支持通过 `subtractionAwardStock` 执行 Redis 计数扣减。

## 5. 数据库表

当前主要业务表位于 `docs/dev-ops/mysql/sql/db.sql`，拆分 SQL 文件也位于同目录。

核心表：

- `award`：奖品基础信息。
- `strategy`：抽奖策略基础配置，包含 `rule_models`。
- `strategy_award`：策略奖品配置，包含库存、概率、排序。
- `strategy_rule`：策略规则配置，如 `rule_weight`、`rule_blacklist`、`rule_luck_award`。
- `rule_tree`、`rule_tree_node`、`rule_tree_node_line`：规则树相关表，目前 DAO/PO 已存在，领域流程尚未完全接入。

## 6. 当前测试入口

### AwardDAOTest

用于验证奖品 DAO 查询：

```java
test_queryAwardList()
```

### StrategyTest

用于验证基础策略装配和随机奖品获取：

```java
test_strategyArmory()
test_getAssembleRandomVal()
```

### StrategyTest2

用于验证增强策略装配、默认抽奖、权重抽奖：

```java
test_getRandomAwardId()
test_getRandomAwardId_ruleWeightValue()
```

注意：权重抽奖依赖 `StrategyArmoryDispatch` 装配权重概率表，因此测试前置装配必须使用 `strategyArmoryDispatch`。

## 7. 近期修复与当前进展

目前已完成以下修复：

- 解除 `vip_marketing_proj-domain` 与 `vip_marketing_proj-infrastructure` 的循环依赖方向问题，使领域层定义接口，基础设施层实现接口。
- 删除 infrastructure 中误创建的重复 `IStrategyRepository`，确保 `StrategyRepository` 实现 domain 模块的仓储接口。
- 修复 MyBatis DAO 返回类型和 XML `resultMap` 类型不一致导致的 `ClassCastException`。
- 修复权重组合 key 被当成 Long 解析导致的 `NumberFormatException`。
- 修复权重概率表未装配导致 `SecureRandom#nextInt(0)` 报 `bound must be positive` 的问题路径。
- 补齐 `StrategyRepository` 中策略查询、规则查询、Redis 概率表读写、库存缓存扣减等关键方法。

## 8. 当前限制与后续方向

当前项目还处于抽奖核心链路建设阶段，以下能力仍需继续完善：

- HTTP API 暂未开放，`trigger` 模块仍是占位结构。
- 规则树相关表、DAO、PO 已存在，但领域层规则树执行链路尚未完成。
- 黑名单规则、兜底奖品规则、库存消费队列等能力只完成了部分模型或接口设计。
- 单元测试以日志验证为主，缺少断言和自动化数据准备。
- Maven Surefire 当前配置包含 `skipTests=true`，完整测试流程需要后续梳理。
- 部分源码注释存在字符编码乱码，建议后续统一文件编码为 UTF-8 后修复注释可读性。

## 9. 推荐运行准备

本地运行或测试前需要准备：

1. 启动 MySQL，并导入 `docs/dev-ops/mysql/sql/db.sql`。
2. 启动 Redis，默认地址为 `127.0.0.1:6379`。
3. 确认 `application-dev.yml` 中数据库连接信息与本地环境一致。
4. 运行策略相关测试时，先保证对应策略数据存在，例如 `100001`、`100002`。

## 10. 核心流程概览

```text
StrategyTest2
  -> StrategyArmoryDispatch#assembleLotteryStrategy
      -> StrategyRepository#queryStrategyAwardList
          -> IStrategyAwardDAO 查询 strategy_award
          -> StrategyAward PO 转 StrategyAwardEntity
      -> 缓存默认概率表
      -> 查询 strategy.rule_models
      -> 查询 strategy_rule.rule_weight
      -> 按权重配置生成多个概率表
      -> 写入 Redis

抽奖
  -> StrategyArmoryDispatch#getRandomAwardId
      -> Redis 读取 rateRange
      -> SecureRandom 生成下标
      -> Redis Map 读取奖品 ID
```
