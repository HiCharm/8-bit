package java_backend.Handler.Update;

import com.sun.net.httpserver.HttpExchange;

import bean.block.Actor;
import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.util.JsonParser;
import java_backend.Handler.BaseHandler;
import java.io.IOException;
import java.util.Map;


// 具体的POST处理器
public class UpdateActorHandler extends BaseHandler {
    private final DataService<Actor> playerActorService;
    private final JsonParser jsonParser;
    
    public UpdateActorHandler(ResponseBuilder responseBuilder, 
                            DataService<Actor> playerActorService, 
                            JsonParser jsonParser) {
        super("/api/player", "POST", responseBuilder);
        this.playerActorService = playerActorService;
        this.jsonParser = jsonParser;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readRequestBody(exchange);
            System.out.println("收到 POST 请求体：" + requestBody);
            
            Actor newActor = jsonParser.extractActor(requestBody);
            if (newActor != null && playerActorService.validateData(newActor)) {
                playerActorService.updateData(newActor);

                Map<String, Object> responseData = Map.of(
                    "player", newActor
                );
                responseBuilder.buildSuccessResponse(exchange, responseData);
            } else {
                responseBuilder.buildErrorResponse(exchange, 400, "参数错误");
            }
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "处理请求时发生错误");
        }
    }
}
