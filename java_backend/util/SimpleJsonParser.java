package java_backend.util;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.TimeZone;


public class SimpleJsonParser implements JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) 
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .setTimeZone(TimeZone.getDefault());

    @SuppressWarnings("unchecked")
    @Override
    public String extractAction(String json) {
        try {
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