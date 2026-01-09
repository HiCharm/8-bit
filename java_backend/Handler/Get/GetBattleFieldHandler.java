package java_backend.Handler.Get;

import com.sun.net.httpserver.HttpExchange;

import bean.map.BaseBattleField;
import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.Handler.BaseHandler;
import java.io.IOException;

public class GetBattleFieldHandler extends BaseHandler {
    private final DataService<BaseBattleField> battleFieldService;

    public GetBattleFieldHandler(ResponseBuilder responseBuilder, DataService<BaseBattleField> battleFieldService) {
        super("/api/battlefield_All", "GET", responseBuilder);
        this.battleFieldService = battleFieldService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            BaseBattleField battleField = battleFieldService.getData();
            responseBuilder.buildSuccessResponse(exchange, battleField);
            System.out.println("处理 GET 请求，返回战场数据，宽度：" + battleField.getWidth() + "，高度：" + battleField.getHeight());
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }
    
}
