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

    public List<List<Actor>> getField() {
        return field;
    }

    public Actor getActorAt(int x, int y) {
        if (y < 0 || y >= field.size() || x < 0 || x >= field.get(y).size()) {
            return null;
        }
        return field.get(y).get(x);
    }

    public void setActorAt(int x, int y, Actor actor) {
        if (y < 0 || y >= field.size() || x < 0 || x >= field.get(y).size()) {
            throw new IllegalArgumentException("坐标(x=" + x + ", y=" + y + ")超出战场范围");
        }
        actor.setPosition(x, y);
        field.get(y).set(x, actor);
    }

    public void filledActor(int x,int y, int width, int height, Actor actor){
        if(width<=0 || height<=0){
            throw new IllegalArgumentException("宽度和高度必须大于0");
        }
        if(x<0 || y<0 || x+width>this.width || y+height>this.height){
            throw new IllegalArgumentException("超出战场范围");
        }

        for(int i=y;i<y+height;i++){
            for(int j=x;j<x+width;j++){
                setActorAt(j, i, actor.copy());
            }
        }
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
