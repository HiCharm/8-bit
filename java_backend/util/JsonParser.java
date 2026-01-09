package java_backend.util;

// JSON解析接口和实现
public interface JsonParser {
    String extractAction(String json);
    Integer extractHealth(String json);
    String toJson(Object obj);
}
