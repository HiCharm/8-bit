import numpy as np

dict = {
    "Empty": 0,
    "Player": 1,
    "smallMonster": 2,
    "bigMonster": 3,
    "Wall": 4
}

label = ["type", "health", "strength", "score"]

class BattleField:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.grid = np.zeros((height, width, len(label)), dtype=int)
    
    def readFromJson(self, json):
        actors = json["actors"]
        # 三维grid，yx坐标，每个位置四个属性，type,health,strength,score
        for actor in actors:
            for item in label:
                self.grid[actor["y"]][actor["x"]][label.index(item)] = actor[item]

    def clear(self):
        self.grid = np.zeros((self.height, self.width, len(label)), dtype=int)

    def getSize(self):
        return self.width, self.height

# {
#     "width":5,
#     "height":5,
#     "actors":[
#         {"health":5,"score":0,"strength":1,"type":"Player","x":0,"y":0},
#         {"health":2,"score":10,"strength":1,"type":"smallMonster","x":0,"y":0},
#         {"health":3,"score":20,"strength":2,"type":"bigMonster","x":0,"y":0},
#         {"health":2,"score":5,"strength":0,"type":"Wall","x":0,"y":0}
#         ]
# }