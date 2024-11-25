package com.nsm_X.Choice;

import com.nsm_X.Main;
import com.nsm_X.Tools.NotificationManager;
import com.nsm_X.Unit.UnitPC.*;

import java.util.Random;

public class DefStat implements StatModifier {
    private Player player;
    private Random random = new Random();
    private Main main;
    float count;



    String notification = "";

    // Pondération pour chaque stat. Par exemple, defense a un poids de 5, speed de 2 et health de 1.
    private float[] weights = {2.5f, 2, 1};  // Les indices correspondent à defense, speed, health.private int[] bigF = {1, 1};  // Les indices correspondent à defense, speed, health.

    // Cumul des poids pour faciliter le tirage pondéré
    private int totalWeight;

    // Constructeur
    public DefStat(Main main) {
        player = main.getPlayerManager().getPlayer();
        this.main=main;
        this.totalWeight = 0;
        for (float weight : weights) {
            totalWeight += weight;
        }
        this.count=main.getFloorCounter() * 0.1f;
    }

    // Choix aléatoire parmi les actions possibles (increase, decrease, restore)
    private int getRandomActionChoice() {
        // Tirage aléatoire parmi les actions : increase, decrease, restore
        return random.nextInt(2); // 0 = increase, 1 = restore
    }

    // Choix pondéré de la stat
    private int getRandomWeightedChoice() {
        int randomValue = random.nextInt(totalWeight);  // Choisit un nombre entre 0 et totalWeight - 1
        int sum = 0;

        // On parcourt les poids pour trouver la stat correspondante
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
            if (randomValue < sum) {
                return i;  // Retourne l'indice du stat choisi en fonction du poids
            }
        }

        return -1;  // En cas d'erreur
    }

    // Fonction restore : réinitialise les stats du joueur
    @Override
    public void restore(Player player) {
        int randomStatChoice = getRandomWeightedChoice();

        switch (randomStatChoice) {
            case 0:  // Defense
                player.setArmor(player.getMaxArmor());
                notification = "RESTORE FULL ARMOR";
                break;
            case 1:  // Health
                player.setHp(player.getMaxHp());
                notification = "RESTORE FULL HP";
                break;
        }
        NotificationManager.setLastNotification(notification);
    }

    // Fonction increase : augmente une stat aléatoire du joueur
    @Override
    public void increase(Player player) {
        int randomStatChoice = getRandomWeightedChoice();  // On récupère le choix pondéré
        int armorIncrease = 5 + (int) count;
        int MaxHpIncrease = 5 + (int) count;
        float ResIncrease = 0.1f+(int)count;

            switch (randomStatChoice) {
            case 0:  // 3
                player.increaseMaxArmor(armorIncrease);// Augmente la défense
                notification = "INCEREASE ARMOR: " + armorIncrease;
                break;
            case 1:  // 2
                player.increaseMaxHp(MaxHpIncrease);// Augmente la vitesse
                notification = "INCREASE MAXHP: " + MaxHpIncrease;
                break;
            case 2:  // 1
                player.increaseResistance(ResIncrease);  // Augmente la santé
                notification = "INCREASE RESISTANCE: " + ResIncrease;
                break;
        }
        NotificationManager.setLastNotification(notification);
    }

    // Fonction decrease : diminue une stat aléatoire de l'ennemi
    @Override
    public void decrease(Player player) {
    }

    // Fonction pour choisir une action (increase, decrease, restore) aléatoirement et l'exécuter
    public void randomChoice() {
        int actionChoice = getRandomActionChoice();  // Choix de l'action à exécuter

        switch (actionChoice) {
            case 0:  // decrease
                increase(player);
                break;
            case 1:  // restore
                // Appeler restore() seulement si les conditions sont remplies
                if (player.getHp() <= 0 || player.getArmor() <= 0) {

                    restore(player);
                } else {
                    // Sinon, choisir une autre action (par exemple increase)
                    increase(player);
                }
                break;
        }
    }

}
