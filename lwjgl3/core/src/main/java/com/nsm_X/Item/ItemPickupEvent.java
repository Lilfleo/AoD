package com.nsm_X.Item;
import com.nsm_X.Unit.UnitPC.Player;

public class ItemPickupEvent  {
    protected ItemData item;
    protected Player player;

    public ItemPickupEvent(ItemData item, Player player) {
        this.item = item;
        this.player = player;
    }
    public void handle(){
        player.equipItem(item);
    }
}
