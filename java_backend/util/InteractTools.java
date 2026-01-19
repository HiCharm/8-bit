package java_backend.util;

import java_backend.DataService.BattleFieldService;
import bean.map.SceneBattle;

public class InteractTools {
    public static void routeInteract(String interactContent){
        
    }
    public static void routeInteract(String interactContent,BattleFieldService battleFieldService){
        if(interactContent.equals("Start I")){
            Interact_Start_I(battleFieldService);
        }else if(interactContent.equals("Start II")){
            Interact_Start_II(battleFieldService);
        }else if (interactContent.equals("Start III")) {
            Interact_Start_III(battleFieldService);
        }
    }

    public static void Interact_Start_I(BattleFieldService battleFieldService){
        battleFieldService.updateData(new SceneBattle(1));
    }
    public static void Interact_Start_II(BattleFieldService battleFieldService){
        battleFieldService.updateData(new SceneBattle(2));
    }
    public static void Interact_Start_III(BattleFieldService battleFieldService){
        battleFieldService.updateData(new SceneBattle(3));
    }

}
