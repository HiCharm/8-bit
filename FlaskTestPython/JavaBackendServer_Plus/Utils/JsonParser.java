package FlaskTestPython.JavaBackendServer_Plus.Utils;

// JSON解析接口和实现
public interface JsonParser {
    Integer extractHealth(String json);
    String toJson(Object obj);
}
