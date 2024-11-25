package com.nsm_X.Item;

import com.nsm_X.Unit.UnitPC.Player;

import java.util.*;

public class InventoryManager {
    protected Player player;
    private List<ItemData> inventory = new ArrayList<>();

    public void addItemToInventory(ItemData item) {
        inventory.add(item);
        System.out.println(item.getName()+"est ajouté a l'inventaire");
    }
    public void useItem(ItemData item) {
        if (item.getType().equals("Consumable")){
            System.out.println(item.getName()+"est utilisé");
        }
    }
}
