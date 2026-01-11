package bean.block;

public class Actor{
    private int health;
    private int color;
    private int strength;
    private String type;

    private int x;
    private int y;

    public int width;
    public int height;
    public boolean nomove;

    public boolean isIntreactive;



    public Actor(int health, int color, int strength, String type, int x, int y, boolean isIntreactive, boolean nomove) {
        this.health = health;
        this.color = color;
        this.strength = strength;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isIntreactive = isIntreactive;
        this.width = 1;
        this.height = 1;
        this.nomove = false;
    }

    public Actor copy(){
        return new Actor(this.health, this.color, this.strength, this.type, this.x, this.y, this.isIntreactive, this.nomove);
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
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