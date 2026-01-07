package test;

import java.io.IOException;
import util.*;

/**
 * 调用 Flask 接口的业务类（纯标准库实现）
 */
public class FlaskApiInvoker {
    // Flask 服务地址（本地测试）
    private static final String FLASK_BASE_URL = "http://127.0.0.1:5000";

    public static void main(String[] args) {
        try {
            // ========== 1. 调用 GET 接口：获取 Player 数据 ==========
            String getUrl = FLASK_BASE_URL + "/api/actor/Player";
            String getResponse = NativeHttpClient.doGet(getUrl);
            System.out.println("GET 接口返回结果：" + getResponse);

            // ========== 2. 调用 POST 接口：更新 Player 血量 ==========
            String postUrl = FLASK_BASE_URL + "/api/actor/update";
            // 构建 JSON 请求体（手动拼接，也可复用你之前的 Actor 类转 JSON）
            String jsonData = "{\"type\":\"Player\",\"health\":100}";
            String postResponse = NativeHttpClient.doPost(postUrl, jsonData);
            System.out.println("POST 接口返回结果：" + postResponse);

        } catch (IOException e) {
            System.err.println("调用 Flask 接口失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
