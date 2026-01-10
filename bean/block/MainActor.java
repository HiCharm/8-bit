package bean.block;

public class MainActor extends Actor {

    public MainActor(int health, int color, int strength, String type, int x, int y) {
        super(health, color, strength, type, x, y, false);
    }

    public MainActor copy() {
        return new MainActor(this.getHealth(), this.getColor(), this.getStrength(), this.getType(), this.getX(),
                this.getY());
    }

}
