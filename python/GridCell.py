import tkinter as tk
from PIL import Image, ImageTk
import os
import random

bg_colors = {
            0: "#CD853F",  # 秘鲁 普通地板
            1: "#FF4500",  # 橙红色 火
            2: "#00BFFF",  # 深天蓝 冰
            3: "#9400D3",  # 深紫罗兰 毒
        }

type_to_path ={
    "Player": "res/player.png",
}

class GridCell:
    """
    场景网格的细分单元格类
    每个实例代表场景中4x4的网格区域
    每个实例占用grid布局的一个格子
    """
    
    def __init__(self, master, unit=None, cellType=0, showHealth=False):
        """
        初始化多图单元格
        
        master: 父容器
        unit: 该单元格上的单位
        cellType: 单元格类型
        showHealth: 是否显示生命值
        """
        self.master = master
        self.unit = unit
        self.showHealth = showHealth
        self.bg_color = bg_colors.get(cellType, "#FFFFFF")

        self.root = tk.Frame(self.master)
        for i in range(4):
            self.root.grid_rowconfigure(i, weight=1)
            self.root.grid_columnconfigure(i, weight=1)

        # 设置背景颜色
        self.root.config(bg=self.bg_color, relief="groove", bd=1)

        if unit:
            self.unitLabel = tk.Label(self.root)
            self.unitLabel.grid(row=0, column=0, rowspan=3 + int(not self.showHealth), columnspan=4, sticky='nsew')
            self.unitLabel.image = ImageTk.PhotoImage(Image.open(type_to_path[self.unit["type"]]))
            self.unitLabel.config(image=self.unitLabel.image, bg=self.bg_color)
            self.root.bind("<Configure>", self.resizeUnitImage)

            self.heartLabels = []
            self.heartQueue = []
            if self.showHealth:
                self.setHealth()
                self.root.bind("<Configure>", self.resizeSmallImage, add="+")

    def resizeUnitImage(self, event):
        w = self.root.winfo_width()
        h = self.root.winfo_height()
        image = Image.open(type_to_path[self.unit["type"]])
        resizedImage = image.resize((w, h * (3 + int(not self.showHealth)) // 4), Image.Resampling.LANCZOS)
        self.unitLabel.image = ImageTk.PhotoImage(resizedImage)
        self.unitLabel.config(image=self.unitLabel.image)

    def resizeSmallImage(self, event):
        w = self.root.winfo_width()
        h = self.root.winfo_height()
        for i, label in enumerate(self.heartLabels):
            image = Image.open(self.heartQueue[i])
            resizedImage = image.resize((w//4, h//4), Image.Resampling.LANCZOS)
            photo = ImageTk.PhotoImage(resizedImage)
            label.config(image=photo)
            label.image = photo

    def grid(self, row, column, rowspan=1, columnspan=1, **kwargs):
        """
        为GridCell提供与tkinter一致的grid方法
        """
        self.root.grid(row=row, column=column, rowspan=rowspan, columnspan=columnspan, **kwargs)

    def setHealth(self):
        """
        设置单元格的生命值
        """
        self.health = self.unit["health"]
        if self.health <=4: # 底色灰色，顶色红色
            for i in range(4):
                if i < self.health:
                    self.heartQueue.append("./res/red_heart.png")
                    self.setImage("./res/red_heart.png", (3, i), (1, 1))
                else:
                    self.heartQueue.append("./res/gray_heart.png")
                    self.setImage("./res/gray_heart.png", (3, i), (1, 1))
        elif self.health <=8: # 底色红色， 顶色橙色
            for i in range(4):
                if i < self.health-4:
                    self.heartQueue.append("./res/orange_heart.png")
                    self.setImage("./res/orange_heart.png", (3, i), (1, 1))
                else:
                    self.heartQueue.append("./res/red_heart.png")
                    self.setImage("./res/red_heart.png", (3, i), (1, 1))
        else:   # 底色橙色，顶色黄色
            for i in range(4):
                if i < self.health-8:
                    self.heartQueue.append("./res/yellow_heart.png")
                    self.setImage("./res/yellow_heart.png", (3, i), (1, 1))
                else:
                    self.heartQueue.append("./res/orange_heart.png")
                    self.setImage("./res/orange_heart.png", (3, i), (1, 1))

    def setImage(self, imagePath, place, shape):
        """
        设置单元格的图片，仅限HealthLabel使用
        imagePath: 图片路径
        place: 图片在4x4网格中的位置(0, 0)
        shape: 图片在4x4网格中的形状(1, 1)
        """
        # 加载图片
        initImage = Image.open(imagePath)
        # 创建一个标签来显示图片
        label = tk.Label(self.root)
        label.grid(row=place[0], column=place[1], rowspan=shape[0], columnspan=shape[1], sticky="news")
        label.image = ImageTk.PhotoImage(initImage)
        label.config(image=label.image, bg=self.bg_color)
        self.heartLabels.append(label)

class GridCellManager:
    def __init__(self, root, grid_size, grid_position=(0, 0), grid_shape=(8, 8), **kwargs):
        '''
        初始化整个网格, grid_shape(8*8个大网格) * 4*4(每个小网格的尺寸) = 总共32*32个网格
        root: 父容器
        grid_size: 所有GridCell(即GridCellManager)的长宽
        grid_position: 网格在父容器中的位置
        grid_shape: 网格的形状
        kwargs: 其他grid的设置(如padx, pady)
        '''

        # 创建一个主框架来容纳所有GridCell
        self.main_frame = tk.Frame(root)
        self.main_frame.grid(row=grid_position[0], column=grid_position[1], sticky="nsew", **kwargs)
        self.grid_shape = grid_shape

        # 配置主框架的网格布局
        for i in range(self.grid_shape[0]):
            self.main_frame.grid_rowconfigure(i, weight=1)
        for i in range(self.grid_shape[1]):
            self.main_frame.grid_columnconfigure(i, weight=1)

        # 配置根窗口的网格布局
        root.grid_rowconfigure(grid_position[0], weight=1)
        root.grid_columnconfigure(grid_position[1], weight=1)

    def initMap(self):
        """
        创建初始地图
        """
        self.grid_cells = []
        for row in range(self.grid_shape[0]):
            for col in range(self.grid_shape[1]):
                cell = GridCell(self.main_frame, unit=None, cellType=(row+col)%4)
                cell.grid(row=row, column=col, sticky="nsew")
                self.grid_cells.append(cell)

    def readMap(self, map):
        self.grid_cells = []
        for row in range(self.grid_shape[0]):
            for col in range(self.grid_shape[1]):
                # 为每个位置创建一个GridCell
                unit = map[row][col]
                if (not unit) or (unit["type"] != "Player"):
                    unit = None 
                cell = GridCell(self.main_frame, unit=unit, cellType=(row + col) % 4)
                cell.grid(row=row, column=col, sticky="nsew")
                self.grid_cells.append(cell)

    def createTestMap(self):
        """
        创建一个测试用的地图
        """
        # 测试用Unit
        unit = {
            "health": 8,
            "type": "Player",
        }
        map = []
        for row in range(self.grid_shape[0]):
            map.append([])
            for col in range(self.grid_shape[1]):
                map[row].append(unit)
        return map

    def checkSize(self, size):
        """
        检查地图是否符合网格大小
        size: 地图的大小
        """
        if size[0] != self.grid_shape[0] or size[1] != self.grid_shape[1]:
            return False
        return True

if __name__ == "__main__":
    # 运行示例
    root = tk.Tk()
    root.geometry("800x600")
    root.title("嵌套网格布局示例")
    
    # 配置根窗口的网格布局
    # root.grid_rowconfigure(0, weight=1)
    # root.grid_columnconfigure(0, weight=1)
    
    grid_cell_manager = GridCellManager(root, grid_size=(800, 600))

    root.mainloop()
