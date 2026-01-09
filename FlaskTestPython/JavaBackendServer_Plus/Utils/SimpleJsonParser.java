package FlaskTestPython.JavaBackendServer_Plus.Utils;

import java.util.Map;

public class SimpleJsonParser implements JsonParser {
    @Override
    public Integer extractHealth(String json) {
        try {
            String healthKey = "\"health\":";
            int startIndex = json.indexOf(healthKey);
            if (startIndex == -1) return null;
            
            startIndex += healthKey.length();
            int endIndex = json.indexOf(",", startIndex);
            if (endIndex == -1) endIndex = json.indexOf("}", startIndex);
            if (endIndex == -1) return null;
            
            String healthStr = json.substring(startIndex, endIndex).trim();
            return Integer.parseInt(healthStr);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String toJson(Object obj) {
        // 简化实现，实际项目中可使用Jackson/Gson
        if (obj instanceof Map) {
            return mapToJson((Map<?, ?>) obj);
        }
        return "{}";
    }
    
    private String mapToJson(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{");
        map.forEach((k, v) -> {
            if (sb.length() > 1) sb.append(",");
            sb.append("\"").append(k).append("\":");
            if (v instanceof String) {
                sb.append("\"").append(v).append("\"");
            } else {
                sb.append(v);
            }
        });
        sb.append("}");
        return sb.toString();
    }
}
