package java_backend.Router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java_backend.Outer.RequestHandler;
import java_backend.Outer.Router;

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