package bean.block;

public class MainActor extends Actor {

    public MainActor(int health, int score, int strength, String type, int x, int y) {
        super(health, score, strength, type, x, y, false);
    }

    public MainActor copy() {
        return new MainActor(this.getHealth(), this.getScore(), this.getStrength(), this.getType(), this.getX(),
                this.getY());
    }

}
