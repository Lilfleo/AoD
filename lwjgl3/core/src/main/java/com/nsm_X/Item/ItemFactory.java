package com.nsm_X.Item;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.nsm_X.Unit.UnitPC.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {

    private static ItemPool itemPool = new ItemPool();
    private static List<ItemData> availableItems = new ArrayList<>();

    // Charger les items depuis le fichier JSON
    public static void loadItemsFromJson() {
        Json json = new Json();
        try {
            // Lire le fichier JSON et convertir en une liste d'objets ItemData
            availableItems = json.fromJson(ArrayList.class, ItemData.class, Gdx.files.internal("item.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Créer un item aléatoire
    public static NewItem createRandomItem() {
        if (availableItems.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(availableItems.size());
        ItemData randomItemData = availableItems.get(randomIndex);

        // Déterminer la quantité aléatoire pour les items consommables 
        int quantity = 1;
        if (!randomItemData.isPermanent()) {  // Si l'item est consommable
            quantity = rand.nextInt(5) + 1;  // Quantité entre 1 et 5
        }

        // Créer l'item
        NewItem item = new NewItem();
        item.initialize(randomItemData.getName(), randomItemData.getId(),
            randomItemData.getType(), quantity,
            (float)(Math.random() * 1000), (float)(Math.random() * 1000));  // Placement aléatoire sur la carte

        // Obtenir l'item du pool et le réinitialiser
        item = itemPool.obtainItem();
        item.reset();

        return item;
    }
    public static void handleItemPickup(Player unit, ItemData item) {
        ItemPickupEvent event = new ItemPickupEvent(item,unit);
        event.handle();  // L'unité ramasse l'item
    }

    // Retourner la liste des items disponibles
    public static List<ItemData> getAvailableItems() {
        return availableItems;
    }

    // Recyclage d'un item
    public static void recycleItem(NewItem item) {
        item.reset();
        itemPool.recycleItem(item);
    }
}
