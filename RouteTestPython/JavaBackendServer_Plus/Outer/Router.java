package RouteTestPython.JavaBackendServer_Plus.Outer;

// 路由管理器接口
public interface Router {
    void addRoute(String path, String method, RequestHandler handler);
    RequestHandler findHandler(String path, String method);
}
