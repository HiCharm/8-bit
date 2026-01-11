from json import dump
import requests

# Java 后端的地址和端口
JAVA_BASE_URL = "http://127.0.0.1:8080"

def test_java_get_api():
    """调用 Java 的 GET 接口：获取 Player 数据"""
    url = f"{JAVA_BASE_URL}/api/player"
    try:
        response = requests.get(url)
        print("=== GET 调用结果 ===")
        print(f"状态码：{response.status_code}")
        print(f"原始响应内容: {repr(response.text)}")
        print(f"响应数据：{response.json()}")
        
    except Exception as e:
        print(f"GET 调用失败：{e}")

def test_java_post_api():
    """调用 Java 的 POST 接口：更新 Player 数据"""
    url = f"{JAVA_BASE_URL}/api/action"
    # 要发送给 Java 的数据
    data = {"action": "down"}
    try:
        # 调试：将data转为json格式并打印
        response = requests.post(url, json=data)
        print("\n=== POST 调用结果 ===")
        
        print(f"响应数据：{response.json()}")
        print(f"原始响应内容: {repr(response.text)}")   
    except Exception as e:
        print(f"POST 调用失败：{e}")

def test_java_user_get_api():
    """调用 Java 的 GET 接口：获取 User Action 数据"""
    url = f"{JAVA_BASE_URL}/api/action"
    try:
        response = requests.get(url)
        print("\n=== User GET 调用结果 ===")
        print(f"状态码：{response.status_code}")
        print(f"原始响应内容: {repr(response.text)}")
        print(f"响应数据：{response.json()}")
        
    except Exception as e:
        print(f"User GET 调用失败：{e}")

def test_java_user_post_api():
    """调用 Java 的 POST 接口：更新 User Action"""
    url = f"{JAVA_BASE_URL}/api/action"
    # 要发送给 Java 的数据
    data = {"action": "down"}
    try:
        # 调试：将data转为json格式并打印
        response = requests.post(url, json=data)
        print("\n=== User POST 调用结果 ===")
        print(f"响应数据：{response.json()}")
        print(f"原始响应内容: {repr(response.text)}")   
    except Exception as e:
        print(f"User POST 调用失败：{e}")

def test_java_battlefield_get_api():
    """调用 Java 的 GET 接口：获取战场全部信息"""
    url = f"{JAVA_BASE_URL}/api/battlefield_All"
    try:
        response = requests.get(url)
        print("\n=== 战场 GET 调用结果 ===")
        print(f"状态码：{response.status_code}")
        print(f"原始响应内容: {repr(response.text)}")
        print(f"响应数据：{response.json()}")
    except Exception as e:
        print(f"战场 GET 调用失败：{e}")


if __name__ == '__main__':
    
    # test_java_battlefield_get_api()

    test_java_get_api()
    test_java_post_api()
    test_java_post_api()
    test_java_get_api()
    
    # test_java_battlefield_get_api()
    