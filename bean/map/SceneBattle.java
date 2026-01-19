package bean.map;

import guide.ActorData;
import bean.block.Actor;
import java.util.Random;

public class SceneBattle extends BaseBattleField {
    private ActorData actorData = new ActorData();
    private Random rnd = new Random();

    public SceneBattle(int difficulty) {
        super(8,30);

        // 放置玩家在固定位置
        setActorAt(4,4,actorData.getActor("Player"));

        // 根据 difficulty 限制 color 的复杂度，最大为4
        int maxColor = Math.max(1, Math.min(4, difficulty));

        // 1) 初始在 y=0 到 y=3 存在怪物（尝试填满这些行）
        for (int y = 0; y <= 3 && y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                // 随机决定是小怪还是大怪（优先小怪，避免过多占位）
                if (shouldSpawnBig(difficulty) && canPlaceBigAt(x, y, difficulty)) {
                    placeBigAt(x, y, difficulty, maxColor);
                    // big 占据多个格子；跳过这些格子的 x
                    int w = bigWidthByDifficulty(difficulty);
                    x += w - 1;
                } else {
                    placeSmallAt(x, y, difficulty, maxColor);
                }
            }
        }

        // 2) 根据 difficulty，在 y >= 8 行上生成额外怪物
        // 我们在 y=8 开始的连续若干行填充，行数与 difficulty 相关
        int startY = 8;
        int extraRows = Math.max(1, Math.min(this.getHeight() - startY, difficulty));
        for (int y = startY; y < startY + extraRows && y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (shouldSpawnBig(difficulty) && canPlaceBigAt(x, y, difficulty)) {
                    placeBigAt(x, y, difficulty, maxColor);
                    int w = bigWidthByDifficulty(difficulty);
                    x += w - 1;
                } else {
                    placeSmallAt(x, y, difficulty, maxColor);
                }
            }
        }
    }

    // 小怪放置
    private void placeSmallAt(int x, int y, int difficulty, int maxColor) {
        int health = 1 + rnd.nextInt(1 + Math.max(0, difficulty));
        int strength = 1 + rnd.nextInt(1 + Math.max(0, difficulty/2));
        int color = 1 + rnd.nextInt(maxColor);
        Actor small = new Actor(health, color, strength, "small", x, y, false, false);
        setActorAt(x, y, small);
    }

    // 大怪放置：通过放置多个同类型 Actor 来表示一个大怪（矩形区域）
    private void placeBigAt(int x, int y, int difficulty, int maxColor) {
        int w = bigWidthByDifficulty(difficulty);
        int h = bigHeightByDifficulty(difficulty);
        int health = 2 + rnd.nextInt(2 + difficulty);
        int strength = 2 + rnd.nextInt(1 + difficulty/2);
        int color = 1 + rnd.nextInt(maxColor);

        for (int yy = y; yy < y + h && yy < this.getHeight(); yy++) {
            for (int xx = x; xx < x + w && xx < this.getWidth(); xx++) {
                // 每个格子作为大怪的一部分，命名为 "big"，并有相同 color
                Actor part = new Actor(health, color, strength, "big", xx, yy, false, false);
                setActorAt(xx, yy, part);
            }
        }
    }

    // 决定是否生成大怪。根据 difficulty 调整概率：difficulty 越高，大怪概率越大
    private boolean shouldSpawnBig(int difficulty) {
        int chance = Math.min(80, 10 + difficulty * 15); // 百分比
        return rnd.nextInt(100) < chance;
    }

    // 大怪宽度随难度改变
    private int bigWidthByDifficulty(int difficulty) {
        return difficulty >= 3 ? 3 : 2;
    }

    // 大怪高度随难度改变
    private int bigHeightByDifficulty(int difficulty) {
        return difficulty >= 4 ? 3 : 2;
    }

    // 检查能否在 (x,y) 放置 big 矩形（不越界且目标格为空）
    private boolean canPlaceBigAt(int x, int y, int difficulty) {
        int w = bigWidthByDifficulty(difficulty);
        int h = bigHeightByDifficulty(difficulty);
        if (x + w > this.getWidth() || y + h > this.getHeight()) return false;
        for (int yy = y; yy < y + h; yy++) {
            for (int xx = x; xx < x + w; xx++) {
                if (getActorAt(xx, yy) != null) return false;
            }
        }
        return true;
    }

}
