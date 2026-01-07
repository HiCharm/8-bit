package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 纯 Java 标准库实现的 HTTP 客户端工具类（无外部依赖）
 */
public class NativeHttpClient {
    // 读取响应数据的通用方法
    private static String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
        );
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    // 发送 GET 请求
    public static String doGet(String url) throws IOException {
        URL requestUrl = new URL(url);
        // 打开 HTTP 连接
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        
        // 设置请求方法和超时
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000); // 连接超时 5 秒
        connection.setReadTimeout(5000);    // 读取超时 5 秒
        
        // 设置请求头（JSON 格式）
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        
        // 获取响应
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("GET 请求失败，响应码：" + responseCode);
        }
        
        // 读取响应体
        String response = readResponse(connection);
        connection.disconnect(); // 关闭连接
        return response;
    }

    // 发送 POST 请求（JSON 数据）
    public static String doPost(String url, String jsonData) throws IOException {
        URL requestUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        
        // 设置请求方法和超时
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        // 允许写入请求体（POST 必须）
        connection.setDoOutput(true);
        
        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/json");
        
        // 写入 JSON 请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // 获取响应
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("POST 请求失败，响应码：" + responseCode);
        }
        
        // 读取响应体
        String response = readResponse(connection);
        connection.disconnect();
        return response;
    }
}