# 玩家行为api

## 玩家行为获取

### 获取接口地址

GET /api/action

### 获得内容

{
  "action": "up" // 玩家当前的行为
}

## 玩家行为更新

### 更新接口地址

POST /api/action

### 请求参数

{
  "action": "up" // 玩家新的行为
}

### 参数接受

“up、down、left、right、nomove、interact、useSkill、useExplosion”
