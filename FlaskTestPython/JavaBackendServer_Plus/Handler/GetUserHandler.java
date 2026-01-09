package FlaskTestPython.JavaBackendServer_Plus.Handler;

import FlaskTestPython.JavaBackendServer_Plus.Outer.DataService;
import FlaskTestPython.JavaBackendServer_Plus.Outer.ResponseBuilder;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;



public class GetUserHandler extends BaseHandler {
    private final DataService<String> userActionService;

    public GetUserHandler(ResponseBuilder responseBuilder, DataService<String> userActionService) {
        super("/api/user/action", "GET", responseBuilder);
        this.userActionService = userActionService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String action = userActionService.getData();
            Map<String, Object> responseData = Map.of(
                "action", action
            );
            responseBuilder.buildSuccessResponse(exchange, responseData);
            System.out.println("处理 GET 请求，返回 User Action 数据，动作：" + action);
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }

}
