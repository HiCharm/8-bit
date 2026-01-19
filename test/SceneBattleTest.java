package test;

import bean.map.SceneBattle;
import bean.map.BaseBattleField;
import bean.block.Actor;

public class SceneBattleTest {
    public static void main(String[] args) {
        testDifficulty(1);
        testDifficulty(3);
        System.out.println("SceneBattleTest: all tests passed");
    }

    private static void testDifficulty(int difficulty){
        BaseBattleField bf = new SceneBattle(difficulty);
        if (bf.getWidth() != 8 || bf.getHeight() != 30) throw new RuntimeException("Size mismatch");

        // 初始 y=0..3 必有怪物
        int initCount = 0;
        for (int y = 0; y <= 3; y++){
            for (int x = 0; x < bf.getWidth(); x++){
                Actor a = bf.getActorAt(x,y);
                if (a != null) initCount++;
            }
        }
        if (initCount == 0) throw new RuntimeException("No monsters in initial area for difficulty="+difficulty);

        // y=8 开始的额外行至少有怪物
        int startY = 8;
        int extraRows = Math.max(1, Math.min(bf.getHeight() - startY, difficulty));
        int extraCount = 0;
        for (int y = startY; y < startY + extraRows; y++){
            for (int x = 0; x < bf.getWidth(); x++){
                Actor a = bf.getActorAt(x,y);
                if (a != null) extraCount++;
            }
        }
        if (extraCount == 0) throw new RuntimeException("No monsters in extra rows for difficulty="+difficulty);

        // small/big 的 color 在 1..maxColor
        int maxColor = Math.max(1, Math.min(4, difficulty));
        for (int y = 0; y < bf.getHeight(); y++){
            for (int x = 0; x < bf.getWidth(); x++){
                Actor a = bf.getActorAt(x,y);
                if (a == null) continue;
                String t = a.getType();
                if ("small".equals(t) || "big".equals(t)){
                    int c = a.getColor();
                    if (c < 1 || c > maxColor) throw new RuntimeException("Color out of range for difficulty="+difficulty+" at ("+x+","+y+"):"+c);
                }
            }
        }

        // 如果存在 big，则至少有一个 big 是多格体（邻居也为 big 且 color 相同）
        boolean anyBig = false;
        boolean bigHasAdj = false;
        for (int y = 0; y < bf.getHeight(); y++){
            for (int x = 0; x < bf.getWidth(); x++){
                Actor a = bf.getActorAt(x,y);
                if (a == null) continue;
                if ("big".equals(a.getType())){
                    anyBig = true;
                    int c = a.getColor();
                    // 检查四邻
                    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
                    for (int[] d: dirs){
                        int nx = x + d[0];
                        int ny = y + d[1];
                        Actor nb = bf.getActorAt(nx, ny);
                        if (nb != null && "big".equals(nb.getType()) && nb.getColor() == c){
                            bigHasAdj = true;
                            break;
                        }
                    }
                }
                if (bigHasAdj) break;
            }
            if (bigHasAdj) break;
        }
        if (anyBig && !bigHasAdj) throw new RuntimeException("Found big(s) but none occupy multiple cells for difficulty="+difficulty);

        System.out.println("SceneBattle difficulty="+difficulty+" OK (init="+initCount+", extra="+extraCount+", anyBig="+anyBig+")");
    }
}
