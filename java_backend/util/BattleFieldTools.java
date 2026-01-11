package java_backend.util;

import bean.map.BaseBattleField;
import bean.block.Actor;
import bean.block.InteractActor;
import java_backend.Outer.DataService;

public class BattleFieldTools {

    public static void moveDownAll(BaseBattleField battleField) {
        for (int y = 0; y < battleField.getHeight(); y++) {
            for (int x = 0; x < battleField.getWidth(); x++) {
                Actor actor = battleField.getActorAt(x, y);
                if (actor != null && !actor.getType().equals("Player")) {
                    MoveActor.moveActorDown(actor, battleField);
                }
            }
        }
    }
    public static void attack(Actor attacker, Actor defender, BaseBattleField battleField){
        int attackStrength = attacker.getStrength();
        int defenderStrength = defender.getStrength();
        int attackHealth = attacker.getHealth();
        int defenderHealth = defender.getHealth();
        defenderHealth -= attackStrength;
        defender.setHealth(defenderHealth);
        // 判断防御者是否死亡，若死亡则从战场移除
        if(MoveActor.actorDied(defender)){
            MoveActor.releaseActor(defender, battleField);
        }
        attackHealth -= defenderStrength;
        attacker.setHealth(attackHealth);
        // 判断攻击者是否死亡，若死亡则从战场移除
        if(MoveActor.actorDied(attacker)){
            MoveActor.releaseActor(attacker, battleField);
        }

    }

    public static void movePlayer(BaseBattleField battleField, int x, int y, int direction, DataService<String> interactService,DataService<String> SelectBlockService){
        String[] direction2String = {"up", "right", "down", "left", "none"};
        Actor player = battleField.getActorAt(x, y);
        int newX = x;
        int newY = y;
        if(direction == 0){
            newY = y+1;
        }else if(direction == 1){
            newX = x+1;
        }else if(direction == 2){
            newY = y-1;
        }else if(direction == 3){
            newX = x-1;
        }else if(direction == -1){
            newX = x;
            newY = y;
            SelectBlockService.updateData("none");
            return;
        }
        boolean moveRes = MoveActor.moveActorTo(player, newX, newY, battleField);

        Actor target = battleField.getActorAt(newX, newY);
        if(moveRes == false){
            // 判断是否移动向可交互目标
            // 若指向的是敌对目标，则进行攻击计算
            if(target != null && target.isIntreactive){
                if(target instanceof InteractActor){

                    SelectBlockService.updateData(direction2String[direction]);
                    InteractActor interactActor = (InteractActor) target;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(target != null && !target.isIntreactive){
                // 敌对目标，进行攻击计算
                attack(player, target, battleField);
            }
        }else{
            // 若指向非可交互目标，则从左、下、右、上依次检查可交互目标
            // 若有可交互目标，则将可交互信息存储进缓存中，等待前端get
            Actor leftTarget = battleField.getActorAt(newX-1, newY);
            Actor downTarget = battleField.getActorAt(newX, newY+1);
            Actor rightTarget = battleField.getActorAt(newX+1, newY);
            Actor upTarget = battleField.getActorAt(newX, newY-1);
            if(leftTarget != null && leftTarget.isIntreactive){
                if(leftTarget instanceof InteractActor){
                    SelectBlockService.updateData("left");
                    InteractActor interactActor = (InteractActor) leftTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(downTarget != null && downTarget.isIntreactive){
                if(downTarget instanceof InteractActor){
                    SelectBlockService.updateData("down");
                    InteractActor interactActor = (InteractActor) downTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(rightTarget != null && rightTarget.isIntreactive){
                if(rightTarget instanceof InteractActor){
                    SelectBlockService.updateData("right");
                    InteractActor interactActor = (InteractActor) rightTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(upTarget != null && upTarget.isIntreactive){
                if(upTarget instanceof InteractActor){
                    SelectBlockService.updateData("up");
                    InteractActor interactActor = (InteractActor) upTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }

        }
        

    }

    
}
