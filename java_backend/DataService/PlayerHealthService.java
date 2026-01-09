package java_backend.DataService;

import java_backend.Outer.DataService;

// 玩家血量服务
public class PlayerHealthService implements DataService<Integer> {
    private int playerHealth = 5;
    
    @Override
    public Integer getData() {
        return playerHealth;
    }
    
    @Override
    public void updateData(Integer health) {
        this.playerHealth = health;
    }
    
    @Override
    public boolean validateData(Integer health) {
        return health != null && health >= 0 && health <= 100; // 假设血量范围0-100
    }
}