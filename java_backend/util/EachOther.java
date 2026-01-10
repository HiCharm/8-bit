package java_backend.util;

import bean.block.Actor;

public class EachOther {

    static public void AattackB(Actor A, Actor B) {
        int newHealth = B.getHealth() - A.getStrength();
        B.setHealth(newHealth);
    }

}
