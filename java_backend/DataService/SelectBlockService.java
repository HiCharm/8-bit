package java_backend.DataService;

import java_backend.Outer.DataService;

public class SelectBlockService implements DataService<String>{
    private String towards;

    @Override
    public String getData() {
        return towards;
    }

    @Override
    public void updateData(String data) {
        this.towards = data;
    }

    @Override
    public boolean validateData(String data) {
        return data != null && !data.isEmpty();
    }
}
