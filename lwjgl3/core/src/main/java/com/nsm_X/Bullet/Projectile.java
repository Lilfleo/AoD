package com.nsm_X.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nsm_X.Unit.Unit;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public abstract class Projectile {
    // Position du tireur
    protected Vector2 position;
    // Direction dans laquelle 
    protected Vector2 direction;
    protected float speed;
    protected float damage;
    protected boolean isEnemyProjectile;
    
    protected Texture texture;

    public Projectile(Vector2 startPosition, Vector2 direction, float speed, float damage, boolean isEnemyProjectile) {
        this.position = startPosition.cpy();
        this.direction = direction.nor();
        this.speed = speed;
        this.damage = damage;
        this.isEnemyProjectile = isEnemyProjectile;
    }

    // Méthode de mise à jour par défaut
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 velocity = new Vector2(direction).scl(speed * delta);
        position.add(velocity);
    }
        // position.add(direction.scl(speed * deltaTime));
    // // Méthode de rendu abstraite
    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    // Collision 
    // public boolean checkCollision(Unit unit) {
    //     if (unit instanceof Enemy){

    //     }else if (unit instanceof Player){

    //     }
    //     float distance = position.dst(unit.getBody().getPosition());
    //     return distance <= unit.getCollisionRadius();
    // }

    // Détection des limites
    public boolean isOutOfBounds(float screenWidth, float screenHeight) {
        return position.x < 0 || position.x > screenWidth;
    }

    public float getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isEnemyProjectile() {
        return isEnemyProjectile;
    }
}
