package com.nsm_X.Item;

public class NewItem {
    private String name;
    private int id;
    private String type;
    private int damage;
    private float x, y;

    // Constructeur
    public NewItem() {}

    // Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // Méthode pour initialiser l'item avec de nouvelles données
    public void initialize(String name, int id, String type, int damage, float x, float y) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }

    // Méthode pour réinitialiser l'item avant de le rendre au pool (pour éviter de conserver des données inutiles)
    public void reset() {
        this.name = "Default";
        this.id = 0;
        this.type = "DefaultType";
        this.damage = 0;
        this.x = 0;
        this.y = 0;
    }
}
