package RouteTestPython.JavaBackendServer_Plus.Router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import RouteTestPython.JavaBackendServer_Plus.Outer.RequestHandler;
import RouteTestPython.JavaBackendServer_Plus.Outer.Router;

// 基于Map的路由管理器
public class MapBasedRouter implements Router {
    private final Map<String, RequestHandler> routes = new ConcurrentHashMap<>();
    
    private String buildKey(String path, String method) {
        return method.toUpperCase() + ":" + path;
    }
    
    @Override
    public void addRoute(String path, String method, RequestHandler handler) {
        routes.put(buildKey(path, method), handler);
    }
    
    @Override
    public RequestHandler findHandler(String path, String method) {
        return routes.get(buildKey(path, method));
    }
}