package test;

import java_backend.util.InteractTools;
import java_backend.Outer.DataService;
import bean.map.BaseBattleField;
import bean.map.SceneBattle;
import bean.block.Actor;

public class InteractToolsTest {

    static class FakeService implements DataService<BaseBattleField> {
        private BaseBattleField data;
        @Override
        public BaseBattleField getData() { return data; }
        @Override
        public void updateData(BaseBattleField data) { this.data = data; }
        @Override
        public boolean validateData(BaseBattleField data) { return data != null; }
    }

    public static void main(String[] args) {
        runAndAssert("Start I", 1);
        runAndAssert("Start II", 2);
        runAndAssert("Start III", 3);
        System.out.println("InteractToolsTest: all tests passed");
    }

    private static void runAndAssert(String cmd, int difficulty) {
        FakeService svc = new FakeService();
        InteractTools.routeInteract(cmd, svc);
        BaseBattleField bf = svc.getData();
        if (bf == null) throw new RuntimeException(cmd + " did not set battlefield");
        if (!(bf instanceof SceneBattle)) throw new RuntimeException(cmd + " did not produce SceneBattle");

        // 检查宽高为 SceneBattle 预期 (8 x 30)
        if (bf.getWidth() != 8 || bf.getHeight() != 30) throw new RuntimeException(cmd + " battlefield size incorrect");

        // 初始 y=0..3 应有怪物
        int initCount = 0;
        for (int y=0;y<=3;y++){
            for (int x=0;x<bf.getWidth();x++){
                Actor a = bf.getActorAt(x,y);
                if (a != null) initCount++;
            }
        }
        if (initCount == 0) throw new RuntimeException(cmd + " initial area has no monsters");

        // y=8 开始的额外行，按 difficulty 至少应有一格被填充
        int startY = 8;
        int extraRows = Math.max(1, Math.min(bf.getHeight() - startY, difficulty));
        int extraCount = 0;
        for (int y = startY; y < startY + extraRows; y++){
            for (int x=0;x<bf.getWidth();x++){
                Actor a = bf.getActorAt(x,y);
                if (a != null) extraCount++;
            }
        }
        if (extraCount == 0) throw new RuntimeException(cmd + " extra rows have no monsters");

        System.out.println(cmd + " OK (initCount="+initCount+", extraCount="+extraCount+")");
    }
}
