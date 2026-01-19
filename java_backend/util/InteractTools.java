package java_backend.util;


import bean.map.BaseBattleField;
import bean.map.SceneBattle;
import java_backend.Outer.DataService;

public class InteractTools {
    public static void routeInteract(String interactContent){
        
    }
    public static void routeInteract(String interactContent,DataService<BaseBattleField> battleFieldService){
        if(interactContent.equals("Start I")){
            Interact_Start_I(battleFieldService);
        }else if(interactContent.equals("Start II")){
            Interact_Start_II(battleFieldService);
        }else if (interactContent.equals("Start III")) {
            Interact_Start_III(battleFieldService);
        }
    }

    public static void Interact_Start_I(DataService<BaseBattleField> battleFieldService){
        battleFieldService.updateData(new SceneBattle(1));
    }
    public static void Interact_Start_II(DataService<BaseBattleField> battleFieldService){
        battleFieldService.updateData(new SceneBattle(2));
    }
    public static void Interact_Start_III(DataService<BaseBattleField> battleFieldService){
        battleFieldService.updateData(new SceneBattle(3));
    }

}
