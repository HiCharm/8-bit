# 战场信息Api

## 获得战场全部信息

### 接口地址

GET /api/battlefield_All

### 获得内容

{
  "width": 10,               // 战场宽度
  "height": 10,              // 战场高度
  "field": [                 // 战场二维数组，表示每个位置的角色信息
    [ Actor, Actor, ... ],
    [ Actor, null,   ... ],
    ...
  ]
}

Actor对象内部
{
    "health" : 5,
    "score" : 0,
    "strength" : 1,
    "type" : "Player",
    "x" : 0,
    "y" : 0
}
