package bean.map;

import java.util.ArrayList;
import java.util.List;

import bean.block.Actor;


public class BaseBattleField {
    protected int width;
    protected int height;
    protected List<List<Actor>> field;

    public BaseBattleField(int width, int height) {
        this.width = width;
        this.height = height;
        // 初始化动态二维结构：嵌套创建ArrayList
        this.field = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Actor> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(null);
            }
            this.field.add(row);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Actor getActorAt(int x, int y) {
        // 增加坐标合法性校验
        if (y < 0 || y >= field.size() || x < 0 || x >= field.get(y).size()) {
            return null;
        }
        // 嵌套ArrayList：field.get(y).get(x)
        return field.get(y).get(x);
    }

    public void setActorAt(int x, int y, Actor actor) {
        // 增加坐标合法性校验
        if (y < 0 || y >= field.size() || x < 0 || x >= field.get(y).size()) {
            throw new IllegalArgumentException("坐标(x=" + x + ", y=" + y + ")超出战场范围");
        }
        actor.setPosition(x, y);
        // 嵌套ArrayList：field.get(y).set(x, actor)
        field.get(y).set(x, actor);
    }

    public void addRow() {
        List<Actor> newRow = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            newRow.add(null);
        }
        field.add(newRow);
        this.height++;
    }

    public void addColumn() {
        for (List<Actor> row : field) {
            row.add(null);
        }
        this.width++;
    }

}
