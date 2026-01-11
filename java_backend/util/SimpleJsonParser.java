package java_backend.util;

import bean.block.Actor;
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
    public Actor extractActor(String json) {
        try {
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            return new Actor((int) map.get("health"), (int) map.get("color"), (int) map.get("strength"), (String) map.get("type"), (int) map.get("x"), (int) map.get("y"), (boolean) map.get("isIntreactive"), (boolean) map.get("nomove"));
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