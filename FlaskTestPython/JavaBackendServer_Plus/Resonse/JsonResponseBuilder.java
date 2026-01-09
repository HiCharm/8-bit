package FlaskTestPython.JavaBackendServer_Plus.Resonse;

import FlaskTestPython.JavaBackendServer_Plus.Outer.ResponseBuilder;
import FlaskTestPython.JavaBackendServer_Plus.Utils.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonResponseBuilder implements ResponseBuilder {
    private final JsonParser jsonParser;
    
    public JsonResponseBuilder(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }
    
    @Override
    public void buildSuccessResponse(HttpExchange exchange, Object data) throws IOException {
        Map<String, Object> response = Map.of("code", 200, "data", data);
        sendResponse(exchange, 200, jsonParser.toJson(response));
    }
    
    @Override
    public void buildErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        Map<String, Object> response = Map.of(
            "code", statusCode,
            "msg", message
        );
        sendResponse(exchange, statusCode, jsonParser.toJson(response));
    }
    
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        
        // 处理OPTIONS预检请求
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, -1);
            return;
        }
        
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}