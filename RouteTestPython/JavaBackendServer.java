package RouteTestPython;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import RouteTestPython.JavaBackendServer_Plus.DataService.*;
import RouteTestPython.JavaBackendServer_Plus.Handler.*;
import RouteTestPython.JavaBackendServer_Plus.Outer.*;
import RouteTestPython.JavaBackendServer_Plus.Resonse.*;
import RouteTestPython.JavaBackendServer_Plus.Router.*;
import RouteTestPython.JavaBackendServer_Plus.Utils.*;

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
            DataService<String> userActionService = new UserActionService();
            
            // 注册处理器
            router.addRoute("/api/actor/Player", "GET", 
                new GetActorHandler(responseBuilder, playerHealthService));
            router.addRoute("/api/actor/update", "POST", 
                new UpdateActorHandler(responseBuilder, playerHealthService, jsonParser));
            router.addRoute("/api/user/action", "GET", 
                new GetUserHandler(responseBuilder, userActionService));
            router.addRoute("/api/user/update", "POST", 
                new UpdateUserHandler(responseBuilder, userActionService, jsonParser));
            
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