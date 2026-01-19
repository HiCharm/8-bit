package java_backend.Handler.Update;

import bean.block.Actor;
import com.sun.net.httpserver.HttpExchange;
import bean.map.BaseBattleField;
import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.util.BattleFieldTools;
import java_backend.util.JsonParser;
import java_backend.Handler.BaseHandler;
import java.io.IOException;
import java.util.Map;

public class UpdateUserHandler extends BaseHandler {
    private final DataService<String> userActionService;
    private final JsonParser jsonParser;
    private final DataService<BaseBattleField> battleFieldService;
    private final DataService<String> InteractService;
    private final DataService<Actor> playerActorService;
    private final DataService<String> SelectBlockService;

    public UpdateUserHandler(ResponseBuilder responseBuilder,
                            DataService<Actor> playerActorService,
                             DataService<String> userActionService,
                             DataService<String> InteractService,
                             DataService<BaseBattleField> battleFieldService,
                             DataService<String> SelectBlockService,
                             JsonParser jsonParser) {
        super("/api/action", "POST", responseBuilder);
        this.userActionService = userActionService;
        this.jsonParser = jsonParser;
        this.InteractService = InteractService;
        this.SelectBlockService = SelectBlockService;
        this.battleFieldService = battleFieldService;
        this.playerActorService = playerActorService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readRequestBody(exchange);
            System.out.println("收到 POST 请求体：" + requestBody);
            

            String newAction = jsonParser.extractAction(requestBody);
            
            if (newAction != null && userActionService.validateData(newAction)) {
                
                userActionService.updateData("");
                

                // 处理玩家行为
                if(newAction.equals("interact")){
                    String interactContent = InteractService.getData();
                    // 处理交互内容

                }else if(newAction.equals("useSkill")){

                }else if(newAction.equals("useExplosion")){

                }else{
                    
                    BattleFieldTools.moveDownAll(battleFieldService.getData());
                    
                    int direction = -1;
                    switch (newAction) {
                        case "up" -> direction = 0;
                        case "right" -> direction = 1;
                        case "down" -> direction = 2;
                        case "left" -> direction = 3;
                    }
                    Actor player = playerActorService.getData();
                    
                    player = BattleFieldTools.movePlayer(battleFieldService.getData(),player.getX(),player.getY(),direction,InteractService,SelectBlockService);
                    
                    playerActorService.updateData(player);
                }

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
