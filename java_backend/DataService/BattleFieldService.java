package java_backend.DataService;

import java_backend.Outer.DataService;
import bean.map.BaseBattleField;

public class BattleFieldService implements DataService<BaseBattleField> {
    private BaseBattleField battleField;

    public BattleFieldService(BaseBattleField battleField) {
        this.battleField = battleField;
    }

    @Override
    public BaseBattleField getData() {
        return battleField;
    }

    @Override
    public void updateData(BaseBattleField data) {
        this.battleField = data;
    }

    @Override
    public boolean validateData(BaseBattleField data) {
        return data != null && data.getWidth() > 0 && data.getHeight() > 0;
    }

}
