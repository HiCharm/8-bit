package RouteTestPython.JavaBackendServer_Plus.Outer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

// 请求处理器接口
public interface RequestHandler {
    boolean canHandle(String path, String method);
    void handle(HttpExchange exchange) throws IOException;
}
