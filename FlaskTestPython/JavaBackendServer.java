package FlaskTestPython;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import FlaskTestPython.JavaBackendServer_Plus.DataService.*;
import FlaskTestPython.JavaBackendServer_Plus.Outer.*;
import FlaskTestPython.JavaBackendServer_Plus.Resonse.*;
import FlaskTestPython.JavaBackendServer_Plus.Router.*;
import FlaskTestPython.JavaBackendServer_Plus.Handler.*;
import FlaskTestPython.JavaBackendServer_Plus.Utils.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaBackendServer {
    private static final int JAVA_SERVER_PORT = 8080;
    private HttpServer server;
    private final Router router;
    private final ResponseBuilder responseBuilder;
    
    public JavaBackendServer(Router router, ResponseBuilder responseBuilder) {
        this.router = router;
        this.responseBuilder = responseBuilder;
    }
    
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(JAVA_SERVER_PORT), 0);
        System.out.println("Java 后端服务启动成功，端口：" + JAVA_SERVER_PORT);
        
        // 设置通用请求处理器
        server.createContext("/", this::handleRequest);
        
        server.setExecutor(null);
        server.start();
    }
    
    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("服务器已停止");
        }
    }
    
    private void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        
        RequestHandler handler = router.findHandler(path, method);
        if (handler != null) {
            handler.handle(exchange);
        } else {
            responseBuilder.buildErrorResponse(exchange, 404, "接口不存在");
        }
    }
    
    public static void main(String[] args) {
        try {
            // 使用依赖注入组装组件
            Router router = new MapBasedRouter();
            JsonParser jsonParser = new SimpleJsonParser();
            ResponseBuilder responseBuilder = new JsonResponseBuilder(jsonParser);
            DataService<Integer> playerHealthService = new PlayerHealthService();
            
            // 注册处理器
            router.addRoute("/api/actor/Player", "GET", 
                new GetActorHandler(responseBuilder, playerHealthService));
            router.addRoute("/api/actor/update", "POST", 
                new UpdateActorHandler(responseBuilder, playerHealthService, jsonParser));
            
            // 启动服务器
            JavaBackendServer backendServer = new JavaBackendServer(router, responseBuilder);
            backendServer.start();
            
            // 添加关闭钩子
            Runtime.getRuntime().addShutdownHook(new Thread(backendServer::stop));
            
        } catch (IOException e) {
            System.err.println("服务器启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}