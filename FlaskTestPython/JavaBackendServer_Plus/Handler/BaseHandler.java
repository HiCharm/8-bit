package FlaskTestPython.JavaBackendServer_Plus.Handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import FlaskTestPython.JavaBackendServer_Plus.Outer.RequestHandler;
import FlaskTestPython.JavaBackendServer_Plus.Outer.ResponseBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// 基础请求处理器（抽象类，提供通用功能）
public abstract class BaseHandler implements RequestHandler {
    protected final String path;
    protected final String method;
    protected final ResponseBuilder responseBuilder;
    
    public BaseHandler(String path, String method, ResponseBuilder responseBuilder) {
        this.path = path;
        this.method = method;
        this.responseBuilder = responseBuilder;
    }
    
    @Override
    public boolean canHandle(String requestPath, String requestMethod) {
        return this.path.equals(requestPath) && this.method.equalsIgnoreCase(requestMethod);
    }
    
    protected String readRequestBody(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();
        return requestBody.toString();
    }
}