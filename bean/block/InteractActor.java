package bean.block;

public class InteractActor extends Actor{

    private String interactContent;
    
    public InteractActor(int health, int score, int strength, String type, int x, int y, boolean isIntreactive, String interactContent) {
        super(health, score, strength, type, x, y, isIntreactive);
        this.interactContent = interactContent;
    }

    public InteractActor copy(){
        return new InteractActor(this.getHealth(), this.getScore(), this.getStrength(), this.getType(), this.getX(), this.getY(), this.isIntreactive,this.interactContent);
    }

    public String getInteractContent() {
        return interactContent;
    }

    public void setInteractContent(String interactContent) {
        this.interactContent = interactContent;
    }
}