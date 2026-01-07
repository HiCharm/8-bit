# AI Coding Agent Instructions for 8-bit Game Project

## Overview
This is a hybrid Java-Python 8-bit style game with Java handling backend game logic and Python providing web/Tkinter frontends. Communication occurs via HTTP REST APIs using only standard libraries (no external JSON/HTTP dependencies).

## Architecture
- **Java Backend**: Core game entities (Actors, BattleField) and HTTP server
- **Python Frontend**: Flask web app and Tkinter GUI client
- **Data Flow**: Python clients ↔ HTTP APIs ↔ Java game logic

## Key Components
- `bean/block/Actor.java`: Game entity with health/score/strength/type/position
- `bean/map/BaseBattleField.java`: 2D grid storing Actor positions
- `function/data/ActorData.java`: Predefined actor templates (Player, smallMonster, bigMonster, Wall)
- `FlaskTestPython/JavaBackendServer.java`: HTTP server exposing `/api/actor/Player` (GET) and `/api/actor/update` (POST)
- `python/app.py`: Flask app proxying requests to Java backend
- `python/frontend.py`: Tkinter client for direct Java API interaction

## Development Workflow
1. **Compile Java**: `javac -d . *.java **/*.java` (recursive compilation)
2. **Run Java Backend**: `java FlaskTestPython.JavaBackendServer` (starts on port 8080)
3. **Run Python Frontend**: `python python/app.py` (Flask on 5000) or `python python/frontend.py` (Tkinter GUI)
4. **Test Integration**: Run `FlaskTestPython/FlaskTest.py` to verify HTTP communication

## Conventions
- **JSON Serialization**: Use `util/MakeJson.java` for Actor/BattleField to JSON conversion (no external libraries)
- **HTTP Communication**: Use `util/NativeHttpClient.java` for Java HTTP requests (pure standard library)
- **Actor Creation**: Always use `ActorData.getActor(type).copy()` to get fresh instances
- **Positioning**: Battlefield uses [y][x] indexing (row-major order)
- **Console Display**: Views in `function/console/view/` render battlefield as `[P][M][ ]` grids

## Examples
- Create battlefield with actors: See `test/Actor2JsonTest.java`
- HTTP API usage: See `FlaskTestPython/FlaskTest.py`
- Frontend integration: See `python/frontend.py` fetch_data_from_backend() method

## Integration Points
- Java backend APIs: GET `/api/actor/Player` returns `{"code":200,"data":{"health":5,"score":0,"strength":1,"x":0,"y":0}}`
- POST `/api/actor/update` with `{"health":200}` updates and returns confirmation
- Python frontends expect JSON responses with "code", "data", "msg" fields</content>
<parameter name="filePath">d:\work\2026\8-bit\.github\copilot-instructions.md