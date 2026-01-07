package bean.map;

import bean.block.Actor;

public class BaseBattleField {
    protected int width;
    protected int height;
    protected Actor[][] field;

    public BaseBattleField(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new Actor[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Actor getActorAt(int x, int y) {
        return field[y][x];
    }

    public void setActorAt(int x, int y, Actor actor) {
        actor.setPosition(x, y);
        field[y][x] = actor;
    }
}
