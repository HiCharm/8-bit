package RouteTestPython.JavaBackendServer_Plus.Handler;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

import RouteTestPython.JavaBackendServer_Plus.Outer.DataService;
import RouteTestPython.JavaBackendServer_Plus.Outer.ResponseBuilder;

import java.util.Map;

// 具体的GET处理器
public class GetActorHandler extends BaseHandler {
    private final DataService<Integer> playerHealthService;
    
    public GetActorHandler(ResponseBuilder responseBuilder, DataService<Integer> playerHealthService) {
        super("/api/actor/Player", "GET", responseBuilder);
        this.playerHealthService = playerHealthService;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            int health = playerHealthService.getData();
            Map<String, Object> responseData = Map.of(
                "health", health,
                "score", 0,
                "strength", 1,
                "x", 0,
                "y", 0
            );
            responseBuilder.buildSuccessResponse(exchange, responseData);
            System.out.println("处理 GET 请求，返回 Player 数据，血量：" + health);
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }
}

