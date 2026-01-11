package java_backend.Handler.Get;
import com.sun.net.httpserver.HttpExchange;
import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.Handler.BaseHandler;
import java.io.IOException;
public class GetSelectBlockHandler extends BaseHandler {
    private final DataService<String> selectBlockService;

    public GetSelectBlockHandler(ResponseBuilder responseBuilder, DataService<String> selectBlockService) {
        super("/api/select/block", "GET", responseBuilder);
        this.selectBlockService = selectBlockService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String selectBlockData = selectBlockService.getData();
            selectBlockService.updateData("none");
            responseBuilder.buildSuccessResponse(exchange, selectBlockData);
            System.out.println("处理 GET 请求，返回选择区块数据：" + selectBlockData);
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }
}
