package java_backend.util;

import bean.block.Actor;
import bean.map.BaseBattleField;

public class MoveActor {
    static public boolean moveActorTo(Actor actor, int newX, int newY, BaseBattleField battleField) {
        if(actor.getType().equals("Player"))
        {
            System.out.println("1moveActorTo: " + actor.getType() + " from (" + actor.getX() + "," + actor.getY() + ") to (" + newX + "," + newY + ")");
        }
        // Check if the new position is within bounds
        if (newX < 0 || newX >= battleField.getWidth() || newY < 0 || newY >= battleField.getHeight()) {
            return false; // Out of bounds
        }
        if(actor.getType().equals("Player"))
        {
            System.out.println("2moveActorTo: " + actor.getType() + " from (" + actor.getX() + "," + actor.getY() + ") to (" + newX + "," + newY + ")");
        }

        // Check if the target position is empty
        if (battleField.getActorAt(newX, newY) != null) {
            return false; // Position occupied
        }

        if(actor.getType().equals("Player"))
        {
            System.out.println("3moveActorTo: " + actor.getType() + " from (" + actor.getX() + "," + actor.getY() + ") to (" + newX + "," + newY + ")");
        }
        // Move the actor
        battleField.setNullAt(actor.getX(), actor.getY()); // Place in new position

        if(actor.getType().equals("Player"))
        {
            System.out.println("4moveActorTo: " + actor.getType() + " from (" + actor.getX() + "," + actor.getY() + ") to (" + newX + "," + newY + ")");
        }

         // Remove from old position
        battleField.setActorAt(newX, newY, actor);
        // actor.setX(newX);

        // actor.setY(newY);
        if(actor.getType().equals("Player"))
        {
            System.out.println("5moveActorTo: " + actor.getType() + " from (" + actor.getX() + "," + actor.getY() + ") to (" + newX + "," + newY + ")");
        }

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
        battleField.setNullAt(actor.getX(), actor.getY());
        return true;
    }

}
