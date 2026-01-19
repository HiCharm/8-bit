package java_backend.util;

import bean.map.BaseBattleField;
import bean.block.Actor;
import bean.block.InteractActor;
import java_backend.Outer.DataService;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

public class BattleFieldTools {

    public static void moveDownAll(BaseBattleField battleField) {
        for (int y = 0; y < battleField.getHeight(); y++) {
            for (int x = 0; x < battleField.getWidth(); x++) {
                Actor actor = battleField.getActorAt(x, y);
                if (actor != null && !actor.getType().equals("Player") && !actor.nomove) {
                    System.out.println("actor type: " + actor.getType());
                    System.out.println("actor nomove: " + actor.nomove);
                    System.out.println("Moving down actor at (" + x + ", " + y + ")");
                    MoveActor.moveActorDown(actor, battleField);
                }
            }
        }
    }
    public static void attack(Actor attacker, Actor defender, BaseBattleField battleField, boolean isFirstAttack){
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
        if(!isFirstAttack){
            return;
        }
        attackHealth -= defenderStrength;
        attacker.setHealth(attackHealth);
        // 判断攻击者是否死亡，若死亡则从战场移除
        if(MoveActor.actorDied(attacker)){
            MoveActor.releaseActor(attacker, battleField);
        }

    }

    /**
     * 连锁攻击：在执行一次基础攻击后，会对与被攻击者相邻且颜色相同的单位继续连锁攻击。
     * 连锁传播按广度优先进行；连锁过程中若攻击者死亡则停止连锁。
     * 连锁攻击复用已有的 {@link #attack(Actor, Actor, BaseBattleField)} 逻辑。
     */
    public static void chainAttackByColor(Actor attacker, Actor defender, BaseBattleField battleField){
        // 先进行一次基础攻击
        attack(attacker, defender, battleField, true);

        // 如果攻击者已死亡，直接返回
        if(MoveActor.actorDied(attacker)){
            return;
        }

        // 使用队列进行广度优先连锁传播，按被攻击单位的颜色向四周扩散
        Queue<Actor> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        q.add(defender);
        visited.add(defender.getX() + "," + defender.getY());

        while(!q.isEmpty()){
            Actor cur = q.poll();
            int color = cur.getColor();
            int cx = cur.getX();
            int cy = cur.getY();

            // 四个方向
            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for(int[] d : dirs){
                int nx = cx + d[0];
                int ny = cy + d[1];
                // 检查边界及是否已访问
                if(nx < 0 || nx >= battleField.getWidth() || ny < 0 || ny >= battleField.getHeight()) continue;
                String key = nx + "," + ny;
                if(visited.contains(key)) continue;

                Actor neighbor = battleField.getActorAt(nx, ny);
                if(neighbor == null) continue;

                // 只对颜色相同的单位触发连锁攻击
                if(neighbor.getColor() == color){
                    // 复用attack逻辑
                    attack(attacker, neighbor, battleField, false);
                    visited.add(key);
                    
                    q.add(neighbor);
                }
            }
        }
    }

    public static Actor movePlayer(BaseBattleField battleField, int x, int y, int direction, DataService<String> interactService,DataService<String> SelectBlockService){
        
        String[] direction2String = {"up", "right", "down", "left", "none"};
        System.out.println("get player");
        Actor player = battleField.getActorAt(x, y);
        System.out.println("movePlayer: " + direction2String[direction] + " from (" + x + ", " + y + ")");

        int newX = x;
        int newY = y;
        if(direction == 0){
            newY = y-1;
        }else if(direction == 1){
            newX = x+1;
        }else if(direction == 2){
            newY = y+1;
        }else if(direction == 3){
            newX = x-1;
        }else if(direction == -1){
            newX = x;
            newY = y;
            SelectBlockService.updateData("none");
            return player;
        }
        
        System.out.println("movePlayer: " + direction2String[direction] + " from (" + x + ", " + y + ") to (" + newX + ", " + newY + ")");
        System.out.println("player type: " + player.getType());
        boolean moveRes = MoveActor.moveActorTo(player, newX, newY, battleField);
        System.out.println("after move:" + player.getX() + " " + player.getY());
        
        Actor target = battleField.getActorAt(newX, newY);

        System.out.println("moveRes: " + moveRes);
        if(moveRes == false){
            // 判断是否移动向可交互目标
            // 若指向的是敌对目标，则进行攻击计算
            if(target != null && target.isIntreactive){
                if(target instanceof InteractActor){
                    System.out.println("false moveRes interactActor");

                    SelectBlockService.updateData(direction2String[direction]);
                    InteractActor interactActor = (InteractActor) target;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(target != null && !target.isIntreactive){
                // 敌对目标，进行攻击计算
                System.out.println("false moveRes attack");
                chainAttackByColor(player, target, battleField);
            }
        }else{
            // 若指向非可交互目标，则从左、下、右、上依次检查可交互目标
            // 若有可交互目标，则将可交互信息存储进缓存中，等待前端g
            System.out.println("after get actor");
            Actor leftTarget = battleField.getActorAt(newX-1, newY);
            System.out.println("after get actor");
            Actor downTarget = battleField.getActorAt(newX, newY+1);
            System.out.println("after get actor");
            Actor rightTarget = battleField.getActorAt(newX+1, newY);
            System.out.println("after get actor");
            Actor upTarget = battleField.getActorAt(newX, newY-1);
            System.out.println("after get actor");
            if(leftTarget != null && leftTarget.isIntreactive){
                if(leftTarget instanceof InteractActor){
                    System.out.println("true moveRes interactActor left");
                    SelectBlockService.updateData("left");
                    InteractActor interactActor = (InteractActor) leftTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(downTarget != null && downTarget.isIntreactive){
                if(downTarget instanceof InteractActor){
                    System.out.println("true moveRes interactActor down");
                    SelectBlockService.updateData("down");
                    InteractActor interactActor = (InteractActor) downTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(rightTarget != null && rightTarget.isIntreactive){
                if(rightTarget instanceof InteractActor){
                    System.out.println("true moveRes interactActor right");
                    SelectBlockService.updateData("right");
                    InteractActor interactActor = (InteractActor) rightTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }else if(upTarget != null && upTarget.isIntreactive){
                if(upTarget instanceof InteractActor){
                    System.out.println("true moveRes interactActor up");
                    SelectBlockService.updateData("up");
                    InteractActor interactActor = (InteractActor) upTarget;
                    String interactContent = interactActor.getInteractContent();
                    interactService.updateData(interactContent);
                }
            }

        }
        return player;

    }

    
}
