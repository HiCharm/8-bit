package java_backend.DataService;

import java_backend.Outer.DataService;

public class InteractService implements DataService<String>{
    private String interactContent;
    @Override
    public String getData() {
        return interactContent;
    }

    @Override
    public void updateData(String data) {
        this.interactContent = data;
    }

    @Override
    public boolean validateData(String data) {
        return data != null && !data.isEmpty();
    }
}
