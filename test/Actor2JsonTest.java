package test;

import function.data.ActorData;
import util.MakeJson;
import bean.map.BaseBattleField;

public class Actor2JsonTest {
    public static void main(String[] args) {
        ActorData actorData = new ActorData();
        System.out.println(actorData.getActor("Player"));
        System.out.println(actorData.getActor("smallMonster"));
        System.out.println(actorData.getActor("bigMonster"));
        System.out.println(actorData.getActor("Wall"));

        BaseBattleField battleField = new BaseBattleField(5, 5);
        battleField.setActorAt(0, 0, actorData.getActor("Player"));
        battleField.setActorAt(1, 1, actorData.getActor("smallMonster"));
        battleField.setActorAt(2, 2, actorData.getActor("bigMonster"));
        battleField.setActorAt(3, 3, actorData.getActor("Wall"));
        System.out.println(MakeJson.battleFieldToJson(battleField));
    }
}
