package java_backend.util;

import bean.block.Actor;

// JSON解析接口和实现
public interface JsonParser {
    String extractAction(String json);
    Actor extractActor(String json);
    String toJson(Object obj);
}
