from flask import Flask, render_template, request, jsonify
import requests

app = Flask(__name__)

# Java后端API地址
JAVA_API_URL = "http://localhost:8080/api"

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/api/communicate', methods=['POST'])
def communicate_with_java():
    try:
        data = request.json
        # 向Java后端发送请求
        response = requests.post(f"{JAVA_API_URL}/process", json=data)
        return jsonify(response.json())
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)