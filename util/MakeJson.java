package util;

import bean.block.Actor;
import bean.map.BaseBattleField;

public class MakeJson {
    
    static public String actorToJson(Actor actor) {
        String json = "{";
        json += "\"health\":" + actor.getHealth() + ",";
        json += "\"score\":" + actor.getScore() + ",";
        json += "\"strength\":" + actor.getStrength() + ",";
        json += "\"type\":\"" + actor.getType() + "\",";
        json += "\"x\":" + actor.getX() + ",";
        json += "\"y\":" + actor.getY();
        json += "}";
        return json;
    }

    static public String battleFieldToJson(BaseBattleField field) {
        String json = "{";
        json += "\"width\":" + field.getWidth() + ",";
        json += "\"height\":" + field.getHeight() + ",";
        json += "\"actors\":[";
        boolean first = true;
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Actor actor = field.getActorAt(x, y);
                if (actor != null) {
                    if (!first) {
                        json += ",";
                    }
                    json += actorToJson(actor);
                    first = false;
                }
            }
        }
        json += "]";
        json += "}";
        return json;
    }
}
