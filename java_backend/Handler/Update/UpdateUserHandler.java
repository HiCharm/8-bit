package java_backend.Handler.Update;

import com.sun.net.httpserver.HttpExchange;

import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.util.JsonParser;
import java_backend.Handler.BaseHandler;
import java.io.IOException;
import java.util.Map;

public class UpdateUserHandler extends BaseHandler {
    private final DataService<String> userActionService;
    private final JsonParser jsonParser;

    public UpdateUserHandler(ResponseBuilder responseBuilder,
                             DataService<String> userActionService,
                             JsonParser jsonParser) {
        super("/api/action", "POST", responseBuilder);
        this.userActionService = userActionService;
        this.jsonParser = jsonParser;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readRequestBody(exchange);
            System.out.println("收到 POST 请求体：" + requestBody);

            String newAction = jsonParser.extractAction(requestBody);
            if (newAction != null && userActionService.validateData(newAction)) {
                userActionService.updateData(newAction);

                Map<String, Object> responseData = Map.of(
                    "action", newAction
                );
                responseBuilder.buildSuccessResponse(exchange, responseData);
            } else {
                responseBuilder.buildErrorResponse(exchange, 400, "参数错误或动作值无效");
            }
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "处理请求时发生错误");
        }
    }
    
}
