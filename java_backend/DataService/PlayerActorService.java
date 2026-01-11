package java_backend.DataService;

import bean.block.Actor;
import java_backend.Outer.DataService;

// 玩家血量服务
public class PlayerActorService implements DataService<Actor> {
    private Actor player;

    public PlayerActorService(Actor player) {
        this.player = player;
    }
    
    @Override
    public Actor getData() {
        return player;
    }
    
    @Override
    public void updateData(Actor player) {
        this.player = player;
    }
    
    @Override
    public boolean validateData(Actor player) {
        return player != null; 
    }
}