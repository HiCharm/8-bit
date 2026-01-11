package bean.map;

import guide.ActorData;

public class SceneBegin extends BaseBattleField {
    private ActorData actorData = new ActorData();
    public SceneBegin() {
        super(12,12);
        filledActor(0,0,2,3,actorData.getActor("CoinEnterI"));
        filledActor(3,0,2,3,actorData.getActor("CoinEnterII"));
        filledActor(6,0,2,3,actorData.getActor("CoinEnterIII"));

        filledActor(0,7,2,4,actorData.getActor("trainPlace"));
        filledActor(9,9,3,2,actorData.getActor("guidePlace"));

        setActorAt(5, 6, actorData.getActor("NPC"));
        setActorAt(6, 6, actorData.getActor("Player"));
    }


}