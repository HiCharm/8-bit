package java_backend.Outer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

// 响应构建器接口
public interface ResponseBuilder {
    void buildSuccessResponse(HttpExchange exchange, Object data) throws IOException;
    void buildErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException;
}
