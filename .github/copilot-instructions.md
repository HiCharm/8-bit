# AI Coding Agent Instructions for 8-bit Game

## 一句话概览
本仓库是一个用纯标准库实现的混合 Java（游戏逻辑）+ Python（前端）项目。前端通过 HTTP REST 与 Java 后端交互；项目刻意不使用外部 JSON/HTTP 库。

## 关键架构要点
- Java 后端：核心实体与游戏逻辑，HTTP 接口（查看 `java_backend/` 和 `Handler/` 下的实现）。
- Python 前端：Flask 代理（`python/app.py`）与 Tkinter 客户端（`python/frontend.py`）。
- 数据流：Python 客户端 ↔ HTTP REST（JSON 格式：`code/data/msg`）↔ Java 游戏逻辑。

## 核心文件速览（示例）
- `bean/block/Actor.java`：Actor 实体（health, score, strength, type, x, y）。
- `bean/map/BaseBattleField.java`：二维网格，注意索引为 `[y][x]`（行优先）。
- `function/data/ActorData.java`：Actor 模板，创建实例请使用 `ActorData.getActor(type).copy()`。
- `util/MakeJson.java` / `util/SimpleJsonParser.java`：项目自制 JSON 序列化/反序列化工具（不要引入外部 JSON 库）。
- `util/NativeHttpClient.java`：Java 端发起 HTTP 请求的工具（标准库实现）。
- `java_backend/Handler/`：路由与处理器（如 `GetActorHandler.java`, `UpdateActorHandler.java`）。

## 运行与调试（常用命令，Windows 环境）
- 编译全部 Java（在项目根目录下）：
```
javac -d . *.java **\*.java
```
- 启动 Java 后端（根据实际包名调整类名；常见做法是运行包含 main 的类）：
```
java java_backend.JavaBackendServer
```
- 启动 Python 前端（Flask 代理）：
```
python python/app.py
```
- 启动 Tkinter 客户端：
```
python python/frontend.py
```

## 项目约定与模式（对 AI 重要）
- 严格使用项目内的 JSON 工具：不要替换为第三方解析器，保持接口兼容。
- HTTP 响应格式稳定：顶层包含 `code`（HTTP-like 状态码）, `data`（对象）, `msg`（可选描述）。
- Actor 实例应通过 `ActorData.getActor(...).copy()` 获取新对象以避免共享可变状态。
- 地图/坐标使用 `[y][x]`（行列），许多视图/渲染代码假定此顺序。

## 常见修改点与注意事项
- 添加新 API：在 `java_backend/Handler/` 下实现 Handler，并注册到 `Router` 实现（查看 `Router/MapBasedRouter.java`）。
- 修改序列化：更新 `util/MakeJson.java` 并同步 `python` 端的 JSON 期望格式。
- 调试端口：默认 Java 服务监听在 8080（请在启动类或配置中确认）。

## 快速定位测试与示例
- 单元/集成示例：`test/Actor2JsonTest.java`（演示 Actor→JSON）
- 前端示例调用：`python/FlaskTest.py`（如存在，或查看 `python/frontend.py` 的 `fetch_data_from_backend()`）。

---
如需我把某个 Handler、序列化或运行问题示例化成可运行的修改/测试，请告诉我想修改的目标文件。反馈我会迭代这份指南。
<parameter name="filePath">d:\work\2026\8-bit\.github\copilot-instructions.md