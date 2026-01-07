package FlaskTestPython;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Java 后端服务端（纯标准库实现）
 * 启动后暴露 HTTP 接口，供 Flask 调用
 */
public class JavaBackendServer {
    // Java 服务端口（避免和 Flask 的 5000 冲突，用 8080）
    private static final int JAVA_SERVER_PORT = 8080;
    // 模拟 Actor 数据（替代原有的 Map 存储）
    private static int playerHealth = 5; // 初始血量

    public static void main(String[] args) {
        try {
            // 1. 创建 HTTP 服务端，绑定 8080 端口
            HttpServer server = HttpServer.create(new InetSocketAddress(JAVA_SERVER_PORT), 0);
            System.out.println("Java 后端服务启动成功，端口：" + JAVA_SERVER_PORT);

            // 2. 注册接口处理器（对应 Flask 要调用的接口）
            // 接口1：GET /api/actor/Player —— 获取 Player 数据
            server.createContext("/api/actor/Player", new GetActorHandler());
            // 接口2：POST /api/actor/update —— 更新 Player 血量
            server.createContext("/api/actor/update", new UpdateActorHandler());

            // 3. 启动服务
            server.setExecutor(null); // 使用默认线程池
            server.start();

        } catch (IOException e) {
            System.err.println("Java 服务启动失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * GET 接口处理器：返回 Player 数据
     */
    static class GetActorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 校验请求方法是否为 GET
            if (!"GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"code\":405,\"msg\":\"只支持 GET 方法\"}");
                return;
            }

            // 构建返回的 Player 数据（JSON 格式）
            String responseJson = String.format(
                    "{\"code\":200,\"data\":{\"health\":%d,\"score\":0,\"strength\":1,\"x\":0,\"y\":0}}",
                    playerHealth
            );

            // 发送响应给 Flask
            sendResponse(exchange, 200, responseJson);
            System.out.println("处理 Flask 的 GET 请求，返回 Player 数据：" + responseJson);
        }
    }

    /**
     * POST 接口处理器：更新 Player 血量
     */
    static class UpdateActorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 校验请求方法是否为 POST
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"code\":405,\"msg\":\"只支持 POST 方法\"}");
                return;
            }

            // 读取 Flask 发送的 JSON 请求体
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)
            );
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            reader.close();

            // 解析 JSON（简化版：手动提取 health，如需完整解析可加 javax.json）
            String requestJson = requestBody.toString();
            System.out.println("收到 Flask 的 POST 请求体：" + requestJson);

            // 提取 health 值（简单字符串处理，适合测试场景）
            int newHealth = extractHealthFromJson(requestJson);
            if (newHealth != -1) {
                playerHealth = newHealth; // 更新血量
                String responseJson = String.format(
                        "{\"code\":200,\"msg\":\"更新成功\",\"data\":{\"type\":\"Player\",\"health\":%d}}",
                        newHealth
                );
                sendResponse(exchange, 200, responseJson);
            } else {
                sendResponse(exchange, 400, "{\"code\":400,\"msg\":\"参数错误，缺少 health\"}");
            }
        }

        // 简易 JSON 解析：提取 health 值（纯字符串处理，无外部库）
        private int extractHealthFromJson(String json) {
            try {
                String healthKey = "\"health\":";
                int startIndex = json.indexOf(healthKey) + healthKey.length();
                int endIndex = json.indexOf(",", startIndex);
                if (endIndex == -1) {
                    endIndex = json.indexOf("}", startIndex);
                }
                String healthStr = json.substring(startIndex, endIndex).trim();
                return Integer.parseInt(healthStr);
            } catch (Exception e) {
                return -1;
            }
        }
    }

    /**
     * 通用响应发送方法
     */
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        // 设置响应头（JSON 格式 + 跨域支持，避免 Flask 调用时报跨域错误）
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST");

        // 发送响应状态码和数据
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        // 写入响应体
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
        exchange.close();
    }
}