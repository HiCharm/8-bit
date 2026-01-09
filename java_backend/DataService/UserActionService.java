package java_backend.DataService;

import java_backend.Outer.DataService;

public class UserActionService implements DataService<String> {
    private String user_action = "up";

    @Override
    public String getData() {
        return user_action;
    }

    @Override
    public void updateData(String data) {
        this.user_action = data;
    }

    @Override
    public boolean validateData(String data) {
        return data != null && (data.equals("up") || data.equals("down") || data.equals("left") || data.equals("right"));
    }
    
}
