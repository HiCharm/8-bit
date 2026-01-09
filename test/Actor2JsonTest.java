package test;

import java_backend.util.JsonParser;
import java_backend.util.SimpleJsonParser;
import bean.block.ActorData;
import bean.map.BaseBattleField;

public class Actor2JsonTest {
    public static void main(String[] args) {
        JsonParser jsonParser = new SimpleJsonParser();

        ActorData actorData = new ActorData();
        System.out.println(actorData.getActor("Player").getType());
        System.out.println(actorData.getActor("smallMonster").getType());
        System.out.println(actorData.getActor("bigMonster").getType());
        System.out.println(actorData.getActor("Wall").getType());

        BaseBattleField battleField = new BaseBattleField(5, 5);
        battleField.setActorAt(0, 0, actorData.getActor("Player"));
        battleField.setActorAt(1, 1, actorData.getActor("smallMonster"));
        battleField.setActorAt(2, 2, actorData.getActor("bigMonster"));
        battleField.setActorAt(3, 3, actorData.getActor("Wall"));
        System.out.println(jsonParser.toJson(battleField));
    }
}
