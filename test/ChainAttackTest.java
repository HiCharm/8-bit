package test;

import bean.map.BaseBattleField;
import bean.block.Actor;
import java_backend.util.BattleFieldTools;

public class ChainAttackTest {
    public static void main(String[] args) {
        BaseBattleField bf = new BaseBattleField(5,5);

        // 创建攻击者（Player）
        Actor player = new Actor(10, 0, 3, "Player", 1, 1, false, false);
        bf.setActorAt(1,1, player);

        // 首次目标（会反击）
        Actor target = new Actor(2, 1, 2, "enemyA", 2, 1, false, false);
        bf.setActorAt(2,1, target);

        // 链上目标 - 颜色相同，应被连锁伤害但不会反击
        Actor chain1 = new Actor(2, 1, 2, "enemyB", 3, 1, false, false);
        bf.setActorAt(3,1, chain1);
        Actor chain2 = new Actor(2, 1, 2, "enemyC", 2, 2, false, false);
        bf.setActorAt(2,2, chain2);

        // 不同颜色的目标，不应受到连锁影响
        Actor other = new Actor(2, 2, 2, "enemyD", 0, 0, false, false);
        bf.setActorAt(0,0, other);

        System.out.println("Before attack:");
        System.out.println("player HP=" + player.getHealth());
        System.out.println("target HP=" + target.getHealth());
        System.out.println("chain1 HP=" + chain1.getHealth());
        System.out.println("chain2 HP=" + chain2.getHealth());
        System.out.println("other HP=" + other.getHealth());

        // 执行连锁攻击
        BattleFieldTools.chainAttackByColor(player, target, bf);

        System.out.println("After chainAttackByColor:");
        System.out.println("player HP=" + player.getHealth() + " (应只因首目标反击而减少)");
        System.out.println("target HP=" + target.getHealth() + " (首目标受伤并可能被移除)");
        System.out.println("chain1 HP=" + chain1.getHealth() + " (连锁受伤，无反击)");
        System.out.println("chain2 HP=" + chain2.getHealth() + " (连锁受伤，无反击)");
        System.out.println("other HP=" + other.getHealth() + " (不受影响)");
    }
}
