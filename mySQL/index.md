# 8-BIT 数据库

## 1. 数据库简介

该数据库存储：
有关可交互对象的事件信息，
道具信息，
角色技能信息

## 2. 数据库设计

### 2.1 可交互对象事件

tab：8Bit_Event
EventID | EventName | EventDescription | EventOptionI | EventOptionII | EventOptionIII

1. 事件ID：唯一标识符，自增长 int
2. 事件名称：事件名称 String
3. 事件描述： 事件描述 String
4. 事件交互选项I： 事件交互选项 String（导向可执行函数）
5. 事件交互选项II： 事件交互选项 String
6. 事件交互选项III： 事件交互选项 String

### 2.2 道具信息

### 2.3 角色技能信息
