package com.nsm_X.Item;

public class ItemData {
    protected final String name;
    protected final int id;
    protected final String type;
    protected int damage;
    protected final boolean perma;

    // Constructeur sans argument pour la désérialisation de LibGDX
    public ItemData() {
        this.name = null;
        this.id = 0;
        this.type = null;
        this.damage = 0;
        this.perma=false;
    }

    // Constructeur pour initialiser les données
    public ItemData(String name, int id, String type, int damage,boolean perma) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.perma=false;
    }

    // Getters (pas de setters car c'est immuable)
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
    public boolean isPermanent(){
        return perma;
    }

    public void setDamage(int damage) {
        // Ici tu pourrais appliquer des validations ou des logiques
        this.damage = damage;
    }

    public void increaseDamage(int amount) {
        this.damage += amount;
    }

    public void decreaseDamage(int amount) {
        this.damage = Math.max(0, this.damage - amount);  // Evite que les dégâts deviennent négatifs
    }
}

