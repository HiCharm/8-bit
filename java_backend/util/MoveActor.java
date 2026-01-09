package java_backend.util;

import bean.block.Actor;
import bean.map.BaseBattleField;

public class MoveActor {
    static public boolean moveActorTo(Actor actor, int newX, int newY, BaseBattleField battleField) {
        // Check if the new position is within bounds
        if (newX < 0 || newX >= battleField.getWidth() || newY < 0 || newY >= battleField.getHeight()) {
            return false; // Out of bounds
        }

        // Check if the target position is empty
        if (battleField.getActorAt(newX, newY) != null) {
            return false; // Position occupied
        }

        // Move the actor
        battleField.setActorAt(actor.getX(), actor.getY(), null); // Remove from old position
        battleField.setActorAt(newX, newY, actor); // Place in new position
        actor.setX(newX);
        actor.setY(newY);

        return true; // Move successful
    }

    static public boolean moveActorDown(Actor actor, BaseBattleField battleField) {
        return moveActorTo(actor, actor.getX(), actor.getY() + 1, battleField);
    }

    static public boolean actorCanMove(Actor actor){
        if(actor.getType().equals("Wall")){
            return false;
        }
        return true;
    }

    static public boolean actorDied(Actor actor){
        if(actor.getHealth()<=0){
            return true;
        }
        return false;
    }

    static public boolean releaseActor(Actor actor, BaseBattleField battleField){
        battleField.setActorAt(actor.getX(), actor.getY(), null);
        return true;
    }

}
