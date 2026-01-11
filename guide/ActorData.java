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
        actorData.put("Player", new Actor(5, 0, 1, "Player", 0, 0, false, false));
        actorData.put("smallMonster", new Actor(2, 10, 1, "smallMonster", 0, 0, false,false));
        actorData.put("bigMonster", new Actor(3, 20, 2, "bigMonster", 0, 0, false,false));
        actorData.put("Wall", new Actor(2, 5, 0, "Wall", 0, 0, false,false));
        
        // add interact actor
        actorData.put("NPC", new InteractActor(1, 0, 0, "NPC", 0, 0, true, "Hello, adventurer!",true));
        actorData.put("CoinEnterI", new InteractActor(1, 0, 0, "CoinEnterI", 0, 0, true, "Start I",true));
        actorData.put("CoinEnterII", new InteractActor(1, 0, 0, "CoinEnterII", 0, 0, true, "Start II",true));
        actorData.put("CoinEnterIII", new InteractActor(1, 0, 0, "CoinEnterIII", 0, 0, true, "Start III",true));
        
        actorData.put("trainPlace", new InteractActor(1, 0, 0, "trainPlace", 0, 0, true, "Train place",true));
        actorData.put("guidePlace", new InteractActor(1, 0, 0, "guidePlace", 0, 0, true, "Guide place",true));
    }

    public Actor getActor(String type){
        Actor actor = actorData.get(type);
        if(actor == null){
            return null;
        }
        return actor.copy();
    }

}
