package java_backend.Handler.Get;

import bean.block.Actor;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import java_backend.Handler.BaseHandler;
import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;

import java.util.Map;

// 具体的GET处理器
public class GetActorHandler extends BaseHandler {
    private final DataService<Actor> playerActorService;
    
    public GetActorHandler(ResponseBuilder responseBuilder, DataService<Actor> playerActorService) {
        super("/api/player", "GET", responseBuilder);
        this.playerActorService = playerActorService;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Actor player = playerActorService.getData();
            Map<String, Object> responseData = Map.of(
                "player", player
            );
            responseBuilder.buildSuccessResponse(exchange, responseData);
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }
}

