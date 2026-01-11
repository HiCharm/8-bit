import tkinter as tk
import requests
from GridCell import GridCellManager as gcm

class FrontendApp:
    def __init__(self, root):
        self.root = root
        self.root.title("8-bit")
        self.root.geometry("800x600")
        self.url = "http://localhost:8080"
        
        # 游戏场景网格
        self.field = gcm(
            self.root, # 根布局
            grid_shape=(12, 12),
            grid_size=(400, 400), # 网格尺寸
            grid_position=(0, 1),  # 在根布局(网格布局)的网格位置
            padx=10, pady=10,  # (其他grid的设置)网格间距
            )
        self.field.readMap(self.field.createTestMap())
        
        # 左侧信息框
        self.leftFrame = tk.Frame(self.root)
        self.leftFrame.grid(row=0, column=0, pady=10, padx=10)

        # 玩家信息
        self.playerInfo = tk.Label(self.leftFrame, text="玩家信息", font=("Arial", 16))
        self.playerInfo.grid(row=0, column=0, pady=10)

        # 右侧信息框
        self.rightFrame = tk.Frame(self.root)
        self.rightFrame.grid(row=0, column=2, pady=10, padx=10)

        # 除了玩家以外其他东西信息
        self.unitInfo = tk.Label(self.rightFrame, text="除了玩家以外其他东西信息", font=("Arial", 16))
        self.unitInfo.grid(row=0, column=0, pady=10)
        
        # 技能信息
        self.skillInfo = tk.Label(self.rightFrame, text="技能信息", font=("Arial", 16))
        self.skillInfo.grid(row=1, column=0, pady=10)

        # debug
        self.debugInfo = tk.Label(self.rightFrame, text="", font=("Arial", 16))
        self.debugInfo.grid(row=2, column=0, pady=10)

        self.getField()

        # # 创建按钮用于发送GET请求
        # self.button = tk.Button(self.root, text="发送GET请求", command=self.getRequest)
        # self.button.grid(row=1, column=0, pady=10)

        # # 创建按钮用于发送POST信息
        # self.postButton = tk.Button(self.root, text="发送POST信息", command=self.postRequest)
        # self.postButton.grid(row=1, column=1, pady=10)

    def postRequest(self, data, api="/api/action"):
        url = self.url + api
        if not isinstance(data, dict):
            data = {"action": data}
        try:
            response = requests.post(url, json=data)
            self.debugInfo.config(text=f"状态码：{response.status_code}\n响应数据：{response.json()}")
        except Exception as e:
            self.debugInfo.config(text=f"连接后端失败: {str(e)}")

    def getField(self, api="/api/battlefield_All"):
        url = self.url + api
        try:
            response = requests.get(url)
            json_data = response.json()["data"]
            w = json_data["width"]
            h = json_data["height"]
            map_data = json_data["field"]
            if self.field.checkSize((w, h)):
                self.field.readMap(map_data)
            else:
                self.field = gcm(
                    self.root, # 根布局
                    grid_shape=(w, h),
                    grid_size=(400, 400), # 网格尺寸
                    grid_position=(0, 1),  # 在根布局(网格布局)的网格位置
                    padx=10, pady=10,  # (其他grid的设置)网格间距
                )
                self.field.readMap(map_data)
        except Exception as e:
            self.debugInfo.config(text=f"GET连接后端失败: {str(e)}")

    def getRequest(self, api):
        url = self.url + api
        try:
            response = requests.get(url)
            json_data = response.json()["data"]
            self.debugInfo.config(text=f"{api}：{json_data}")
        except Exception as e:
            self.debugInfo.config(text=f"GET连接后端失败: {str(e)}")

    # 上下左右，交互，技能，大招
    def moveUp(self, event=None):
        self.postRequest("up")
    
    def moveDown(self, event=None):
        self.postRequest("down")
    
    def moveLeft(self, event=None):
        self.postRequest("left")
    
    def moveRight(self, event=None):
        self.postRequest("right")
    
    def interact(self, x, event=None):
        self.postRequest(f"{x}")

    def useSkill(self, event=None):
        self.postRequest("useSkill")        

    def useExplosion(self, event=None):
        self.postRequest("useExplosion")

    def bindKeys(self):
        self.root.bind("<Up>", self.moveUp)
        self.root.bind("<Down>", self.moveDown)
        self.root.bind("<Left>", self.moveLeft)
        self.root.bind("<Right>", self.moveRight)
        self.root.bind("w", self.moveUp)
        self.root.bind("s", self.moveDown)
        self.root.bind("a", self.moveLeft)
        self.root.bind("d", self.moveRight)
        self.root.bind("i", self.interact)
        self.root.bind("j", self.useSkill)
        self.root.bind("k", self.useExplosion)
    

if __name__ == "__main__":
    root = tk.Tk()
    app = FrontendApp(root)
    app.bindKeys()
    root.mainloop()