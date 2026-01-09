package RouteTestPython.JavaBackendServer_Plus.Utils;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SimpleJsonParser implements JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    @Override
    public String extractAction(String json) {
        try {
            // 使用Jackson的正规解析方法
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            return (String) map.get("action");
        } catch (Exception e) {
            System.err.println("JSON解析异常: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer extractHealth(String json) {
        try {
            // 使用Jackson的正规解析方法
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            return (Integer) map.get("health");
        } catch (Exception e) {
            System.err.println("JSON解析异常: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.err.println("JSON序列化异常: " + e.getMessage());
            return "{}";
        }
    }
}
