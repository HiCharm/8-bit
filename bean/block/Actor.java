package bean.block;

public class Actor{
    private int health;
    private int score;
    private int strength;
    private String type;

    private int x;
    private int y;

    boolean isIntreactive;

    public Actor(int health, int score, int strength, String type, int x, int y, boolean isIntreactive) {
        this.health = health;
        this.score = score;
        this.strength = strength;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isIntreactive = isIntreactive;
    }

    public Actor copy(){
        return new Actor(this.health, this.score, this.strength, this.type, this.x, this.y, this.isIntreactive);
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
} 