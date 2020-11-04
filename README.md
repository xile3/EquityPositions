## 需求点:
The Positions should be updated after each transaction e.g. once transaction 1 and 2 has
arrived, the positions would be REL= +50 and ITC= -40
INSERT / UPDATE / CANCEL are actions on a Trade (with same trade id but different version)
INSERT will always be 1st version of a Trade, CANCEL will always be last version of Trade.
For UPDATE, SecurityCode or Quantity or Buy/Sell can change
For CANCEL, any changes in SecurityCode or Quantity or Buy/Sell may change and should be
ignored
The transactions can arrive in any sequence

## 需求点分析:
备注:actionType 操作类型，INSERT/UPDATE/CANCEL
1.交易在第一次操作时做插入操作，数据初始 id自增,tradeId、version 初始为1
  1.1.交易在第二次购买操作时做插入操作，则操作类型actionType为UPDATE，此时version加1
  1.2. 交易做出手(Sell)操作时做插入操作，则操作类actionType为INSERT,此时tradeId加1
2.已交易的记录做取消操作
  2.1. 选中(这里已将要取消的记录主键Id返回)记录后，做取消操作，此时只将应记录actionType修改为CANCEL
<br>
## 设计:
1.因新增、更新、取消tradeId不变，因此，在插入记录时，先根据SecurityCode、actionType(INSERT)查找出对应记录，根据version倒序,limit 1
  同时统计出SecurityCode对应记录中，tradeId、version的最大值用来计算新的记录的tradeId和version值。
  1.1.如果该维度查询的记录为空，则新增一条Insert,前提是operationType=Buy
  1.2.有记录,则新增一条，同时设置actionType为UPDATE且version+1

2.取消操作根据主键做更新操作，值更新主键对应的actionType为CANCEL
3.各securityCode分组统计交易数量,这里略去了分页查询。
根据securityCode分组统计总数，如果记录中有Sell操作，则将值转换为负值做求和计算。
<br>
## 数据库建表JPA自动创建

### 数据防护(这里只列举防护项)
1.用户鉴权
2.请求传入参数做有效性验证
3.水平权限校验
4.控制page size 大小
5.防SQL 注入


## 工程结构图(整体结构)
position root根节点
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─example
│  │  │          └─equity
│  │  │              └─positions
│  │  │                  ├─common      基础服务包
│  │  │                  ├─controller  对外暴露的接口
│  │  │                  │  └─dto     数据传输对象
│  │  │                  ├─domain       实体模型
│  │  │                  │  ├─entity   数据表对象模型
│  │  │                  │  ├─mapper    数据持久层
│  │  │                  │  └─repository
│  │  │                  │      └─impl
│  │  │                  └─service        服务层
│  │  │                  │   └─impl       服务层业务逻辑实现
│  │  │                  │
│  │  │                  └─ Equity Application  服务启动类
│  │  └─resources     资源列表
│  │      ├─lib
│  │      ├─mapper   SQL对应的XML文件
│  │      ├─static
│  │      └─templates
│  └─test
│      └─java
│          └─com
│              └─example
│                  └─equity
│                      └─positions
│                          ├─controller  controller 层测试用例
│                          └─service
│                              └─impl   服务层接口测试用例

