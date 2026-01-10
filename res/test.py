import tkinter as tk
from PIL import Image, ImageTk

class app:
    def __init__(self, root):
        self.root = root
        self.root.title("网格布局示例")
        self.root.geometry("800x600")  # 初始窗口大小
        image1 = Image.open("res/20260110_150013.png")
        self.colors = ["red", "orange", "yellow", "gray"]

        # 确保窗口大小变化时网格能自适应
        for i in range(4):
            self.root.grid_rowconfigure(i, weight=1)  # 4行，权重均为1
        for j in range(4):
            self.root.grid_columnconfigure(j, weight=1)  # 4列，权重均为1

        big_label = tk.Label(self.root)
        big_label.grid(row=0, column=0, rowspan=3, columnspan=4, sticky='nsew')
        self.big_label = big_label
        big_label.image = ImageTk.PhotoImage(image1)
        big_label.config(image=big_label.image)
        self.root.bind("<Configure>", lambda event, image=image1: self.resizeBigImage(event, image))
        
        self.small_labels = []
        for i in range(4):
            small_label = tk.Label(self.root)
            small_label.grid(row=3, column=i)
            image = Image.open(f"res/{self.colors[i]}_heart.png")
            small_label.image = ImageTk.PhotoImage(image)
            small_label.config(image=small_label.image)
            self.small_labels.append(small_label)
        self.root.bind("<Configure>", self.resizeImage, add="+")

    def resizeBigImage(self, event, originalImage):
        w = self.root.winfo_width()
        h = self.root.winfo_height()
        newSize = (w, h*3 // 4)
        resizedImage = originalImage.resize(newSize, Image.LANCZOS)
        self.big_label.image = ImageTk.PhotoImage(resizedImage)
        self.big_label.config(image=self.big_label.image)

    def resizeImage(self, event):   
        for i in range(4):
            w = self.root.winfo_width()
            h = self.root.winfo_height()
            newSize = (w // 4, h // 4)
            print(f"index {i}: w: {w}, h: {h}, newSize: {newSize}")
            resizedImage = Image.open(f"res/{self.colors[i]}_heart.png").resize(newSize, Image.LANCZOS)
            self.small_labels[i].image = ImageTk.PhotoImage(resizedImage)
            self.small_labels[i].config(image=self.small_labels[i].image)


if __name__ == "__main__":
    root = tk.Tk()
    app(root)
    root.mainloop()
