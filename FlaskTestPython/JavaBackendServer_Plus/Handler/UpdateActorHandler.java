package FlaskTestPython.JavaBackendServer_Plus.Handler;

import FlaskTestPython.JavaBackendServer_Plus.Outer.DataService;
import FlaskTestPython.JavaBackendServer_Plus.Outer.ResponseBuilder;
import FlaskTestPython.JavaBackendServer_Plus.Utils.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;


// 具体的POST处理器
public class UpdateActorHandler extends BaseHandler {
    private final DataService<Integer> playerHealthService;
    private final JsonParser jsonParser;
    
    public UpdateActorHandler(ResponseBuilder responseBuilder, 
                            DataService<Integer> playerHealthService, 
                            JsonParser jsonParser) {
        super("/api/actor/update", "POST", responseBuilder);
        this.playerHealthService = playerHealthService;
        this.jsonParser = jsonParser;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readRequestBody(exchange);
            System.out.println("收到 POST 请求体：" + requestBody);
            
            Integer newHealth = jsonParser.extractHealth(requestBody);
            if (newHealth != null && playerHealthService.validateData(newHealth)) {
                playerHealthService.updateData(newHealth);
                
                Map<String, Object> responseData = Map.of(
                    "type", "Player", 
                    "health", newHealth
                );
                responseBuilder.buildSuccessResponse(exchange, responseData);
            } else {
                responseBuilder.buildErrorResponse(exchange, 400, "参数错误或血量值无效");
            }
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "处理请求时发生错误");
        }
    }
}
