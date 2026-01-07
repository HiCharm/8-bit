import tkinter as tk
import requests
import datetime

class FrontendApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Python-Java 通信示例")
        self.root.geometry("500x300")
        
        # 创建标签用于显示后端返回的JSON内容
        self.label = tk.Label(root, text="等待从后端接收数据...", wraplength=450, justify="left")
        self.label.pack(pady=20)
        
        # 创建按钮用于发送消息到后端
        self.button = tk.Button(root, text="发送消息到后端", command=self.send_message)
        self.button.pack(pady=10)
        
        # Java后端API地址
        self.java_api_url = "http://localhost:8080/api/process"
        
        # 初始化时尝试从后端获取数据
        self.fetch_data_from_backend()
    
    def fetch_data_from_backend(self):
        """从后端获取数据并显示在标签上"""
        try:
            response = requests.get(self.java_api_url)
            if response.status_code == 200:
                data = response.json()
                
                self.label.config(text=f"从后端接收到的数据:\n{data}")
            else:
                self.label.config(text=f"获取数据失败: {response.status_code}")
        except Exception as e:
            self.label.config(text=f"连接后端失败: {str(e)}")
    
    def send_message(self):
        """发送消息到后端"""
        try:
            # 构建要发送的消息
            current_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            message = f"Received {current_time}"
            
            # 发送POST请求到后端
            response = requests.post(self.java_api_url, json={"message": message})
            
            if response.status_code == 200:
                # 更新标签显示后端返回的响应
                data = response.json()
                self.label.config(text=f"发送成功!\n后端响应:\n{data}")
            else:
                self.label.config(text=f"发送失败: {response.status_code}")
        except Exception as e:
            self.label.config(text=f"发送消息失败: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = FrontendApp(root)
    root.mainloop()