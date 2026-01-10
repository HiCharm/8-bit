package java_backend.Handler.Get;
import com.sun.net.httpserver.HttpExchange;


import java_backend.Outer.DataService;
import java_backend.Outer.ResponseBuilder;
import java_backend.Handler.BaseHandler;
import java.io.IOException;

public class GetInteractContentHandler extends BaseHandler {
    private final DataService<String> interactContentService;

    public GetInteractContentHandler(ResponseBuilder responseBuilder, DataService<String> interactContentService) {
        super("/api/interact/content", "GET", responseBuilder);
        this.interactContentService = interactContentService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String interactContent = interactContentService.getData();

            // 将交互内容进行sql映射

            responseBuilder.buildSuccessResponse(exchange, interactContent);
            System.out.println("处理 GET 请求，返回交互内容数据：" + interactContent);
        } catch (Exception e) {
            responseBuilder.buildErrorResponse(exchange, 500, "服务器内部错误");
        }
    }
    
}
