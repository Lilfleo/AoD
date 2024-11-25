package com.nsm_X.Tools;

import java.util.Hashtable;
import java.util.Random;

public class MapManager {
    private Hashtable<String, String> mapTable;
    private String curentMap = "map/Map00.tmx"; // Carte initiale


    public MapManager() {
        mapTable = new Hashtable<String, String>();
        // Initialiser la table des cartes
        mapTable.put("Map01", "map/Map01.tmx");
        mapTable.put("Map02", "map/Map02.tmx");
        mapTable.put("Map03", "map/Map03.tmx");
        mapTable.put("Map04", "map/Map04.tmx");
        mapTable.put("Map05", "map/Map05.tmx");
    }

    // Méthode pour sélectionner une carte aléatoire différente de la précédente
    public String randomMap() {
        // Tableau des clés de la HashMap
        String[] maps = mapTable.keySet().toArray(new String[mapTable.size()]);

        String selectedMap;
        Random rand = new Random();

        do {
            // Sélectionner une carte aléatoire parmi les clés
            selectedMap = maps[rand.nextInt(maps.length)];
        } while (mapTable.get(selectedMap).equals(curentMap)); // Vérifier que la carte n'est pas la même que la précédente

        // Mise à jour de la carte actuelle
        curentMap = mapTable.get(selectedMap);

     // Affichage de l'étage actuel

     // Retourner la nouvelle carte sélectionnée
        return curentMap;
    }

    // Méthode pour obtenir la carte actuelle
    public String getCurentMap() {
        return curentMap;
    }
}

