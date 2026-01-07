package function.console.view;

import bean.map.BaseBattleField;
import bean.block.Actor;

public class GamingView extends View{

    private BaseBattleField battleField;

    public GamingView() {
        this.battleField = null;
    }

    public GamingView(BaseBattleField battleField) {
        this.battleField = battleField;
    }


    @Override
    public void show() {
        clear();
        if (battleField == null) {
            System.out.println("No battle field to display.");
            return;
        }
        for (int y = 0; y < battleField.getHeight(); y++) {
            for (int x = 0; x < battleField.getWidth(); x++) {
                Actor actor = battleField.getActorAt(x, y);
                if (actor != null) {
                    System.out.print("[" + actor.getType().charAt(0) + "]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
        
    }

    @Override
    public void updata() {
        
    }
    
}
