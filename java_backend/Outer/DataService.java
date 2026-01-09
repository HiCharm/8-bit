package java_backend.Outer;

// 数据服务接口（支持不同类型的数据操作）
public interface DataService<T> {
    T getData();
    void updateData(T data);
    boolean validateData(T data);
}
