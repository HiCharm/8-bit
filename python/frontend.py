import tkinter as tk
import requests
import BattleField

class FrontendApp:
    def __init__(self, root):
        self.root = root
        self.root.title("8-bit")
        self.root.geometry("800x600")
        self.battleField = BattleField.BattleField(8, 8)
        self.url = "http://localhost:8080"

        # 游戏场景网格
        self.grid = tk.Frame(root)
        self.grid.pack(pady=10)
        # 游戏场景网格，8*8，每个格子一个标签
        self.gridLabels = []
        for y in range(self.battleField.getSize()[1]):
            row = []
            for x in range(self.battleField.getSize()[0]):
                label = tk.Label(self.grid, text="", width=2, height=1, borderwidth=1, relief="solid")
                label.grid(row=y, column=x)
                row.append(label)
            self.gridLabels.append(row)
        
        # 创建标签用于显示后端返回的JSON内容

        self.label = tk.Label(root, text="NAN", wraplength=450, justify="left")
        self.label.pack(pady=20)
        
        # 创建按钮用于发送GET请求
        self.button = tk.Button(root, text="发送GET请求", command=self.getRequest)
        self.button.pack(pady=10)

        # 创建按钮用于发送POST信息
        self.postButton = tk.Button(root, text="发送POST信息", command=self.postRequest)
        self.postButton.pack(pady=10)

    def postRequest(self, data):
        url = self.url + "/api/actor/update"

        if not isinstance(data, dict):
            data = {"type": data}
        
        try:
            response = requests.post(url, json=data)
            self.label.config(text=f"状态码：{response.status_code}\n响应数据：{response.json()}")
        except Exception as e:
            self.label.config(text=f"连接后端失败: {str(e)}")

    def getRequest(self):
        url = self.url + "/api/actor/Player"
        try:
            response = requests.get(url)
            self.label.config(text=f"状态码：{response.status_code}\n响应数据：{response.json()}")
        except Exception as e:
            self.label.config(text=f"连接后端失败: {str(e)}")

    # 上下左右，交互，技能，大招
    def moveUp(self, event=None):
        self.postRequest({"type": "up"})
    
    def moveDown(self, event=None):
        self.postRequest({"type": "down"})
    
    def moveLeft(self, event=None):
        self.postRequest({"type": "left"})
    
    def moveRight(self, event=None):
        self.postRequest({"type": "right"})
    
    def interact(self, x, event=None):
        self.postRequest({"type": f"{x}"})

    def useSkill(self, event=None):
        self.postRequest({"type": "useSkill"})

    def useExplosion(self, event=None):
        self.postRequest({"type": "useExplosion"})

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