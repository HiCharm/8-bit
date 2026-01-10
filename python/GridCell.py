import tkinter as tk
from tkinter import PhotoImage
from PIL import Image, ImageTk
import os
import random


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
        self.cellType = cellType

        self.root = tk.Frame(self.master)
        for i in range(4):
            self.root.grid_rowconfigure(i, weight=1)
            self.root.grid_columnconfigure(i, weight=1)

        self.setType()

        if unit:
            self.unitLabel = tk.Label(self.root)
            self.unitLabel.grid(row=0, column=0, rowspan=3, columnspan=4, sticky='nsew')
            self.unitLabel.image = ImageTk.PhotoImage(Image.open(self.unit["imagePath"]))
            self.unitLabel.config(image=self.unitLabel.image)
            self.root.bind("<Configure>", self.resizeUnitImage)
            if showHealth:
                self.heartLabels = []
                self.heartQueue = []
                self.setHealth()
                self.root.bind("<Configure>", self.resizeSmallImage, add="+")

    def resizeUnitImage(self, event):
        w = self.root.winfo_width()
        h = self.root.winfo_height()
        image = Image.open(self.unit["imagePath"])
        resizedImage = image.resize((w, h*3//4), Image.Resampling.LANCZOS)
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

    def setType(self):
        """
        设置单元格的类型
        每个GridCell代表场景中4x4的网格区域
        """
        # 根据单元格类型设置不同的背景
        bg_colors = {
            0: "#FFFFFF",  # 普通单元格
            1: "#E0E0E0",  # 地形单元格
            2: "#FFE0E0",  # 危险单元格
            3: "#E0FFE0",  # 安全单元格
        }
        
        bg_color = bg_colors.get(self.cellType, "#FFFFFF")
        self.root.config(bg=bg_color, relief="groove", bd=1)
        
        # 移除小网格之间的分割线，只保留大网格之间的分割线
        
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
        label.bind("<Configure>", 
                    lambda event, label=label, initImage=initImage: self.resizeImage(event, label, initImage),
                    add="+")
        label.image = ImageTk.PhotoImage(initImage)
        label.config(image=label.image)
        self.heartLabels.append(label)

class GridCellManager:
    def __init__(self, root, grid_size, grid_position=(0, 0), grid_shape=(8, 8)):
        '''
        初始化整个网格, grid_shape(8*8个大网格) * 4*4(每个小网格的尺寸) = 总共32*32个网格
        root: 父容器
        grid_size: 所有GridCell(即GridCellManager)的长宽
        grid_position: 网格在父容器中的位置
        grid_shape: 网格的形状
        '''

        # 创建一个主框架来容纳所有GridCell
        self.main_frame = tk.Frame(root)
        self.main_frame.grid(row=grid_position[0], column=grid_position[1], sticky="nsew")
        
        # 配置主框架的网格布局
        for i in range(grid_shape[0]):
            self.main_frame.grid_rowconfigure(i, weight=1)
        for i in range(grid_shape[1]):
            self.main_frame.grid_columnconfigure(i, weight=1)

        # 配置根窗口的网格布局
        root.grid_rowconfigure(grid_position[0], weight=1)
        root.grid_columnconfigure(grid_position[1], weight=1)

        # 测试用Unit
        self.unit = {
            "health": 10,
            "type": 0,
            "imagePath": "./res/20260110_150013.png"
        }

        self.grid_cells = []
        for row in range(grid_shape[0]):
            for col in range(grid_shape[1]):
                # 为每个位置创建一个GridCell
                cell_type = (row + col) % 4  # 为了演示效果，使用不同的单元格类型
                cell = GridCell(self.main_frame, unit=random.choice([self.unit, None]), cellType=cell_type)
                cell.grid(row=row, column=col, sticky="nsew")
                self.grid_cells.append(cell)


if __name__ == "__main__":
    # 运行示例
    root = tk.Tk()
    root.geometry("800x600")
    root.title("嵌套网格布局示例")
    
    # 配置根窗口的网格布局
    root.grid_rowconfigure(0, weight=1)
    root.grid_columnconfigure(0, weight=1)
    
    grid_cell_manager = GridCellManager(root, grid_size=(800, 600))

    root.mainloop()

    