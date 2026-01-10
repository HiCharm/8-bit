package guide;

import java.util.Map;

import bean.block.Actor;
import bean.block.InteractActor;

import java.util.HashMap;

public class ActorData {
    // save actor data
    Map<String, Actor> actorData;
    
    public ActorData(){
        actorData = new HashMap<>();
        // here add new actor
        // data: health, score, strength, type, x, y
        actorData.put("Player", new Actor(5, 0, 1, "Player", 0, 0, false));
        actorData.put("smallMonster", new Actor(2, 10, 1, "smallMonster", 0, 0, false));
        actorData.put("bigMonster", new Actor(3, 20, 2, "bigMonster", 0, 0, false));
        actorData.put("Wall", new Actor(2, 5, 0, "Wall", 0, 0, false));
        // add interact actor
        actorData.put("Chest", new InteractActor(1, 0, 1, "Chest", 0, 0, true, "This is a chest"));
    }

    public Actor getActor(String type){
        Actor actor = actorData.get(type);
        if(actor == null){
            return null;
        }
        return actor.copy();
    }

}
