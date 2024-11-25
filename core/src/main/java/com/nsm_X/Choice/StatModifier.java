package com.nsm_X.Choice;

import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public interface StatModifier {

    public void increase(Player player); //pour les stats flat
    public void decrease(Player player); //pour les stats flat
    public void restore(Player player);

}
