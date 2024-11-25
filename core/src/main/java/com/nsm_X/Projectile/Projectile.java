package com.nsm_X.Projectile;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nsm_X.Main;

public abstract class Projectile extends Sprite {

    private Vector2 position;
    private Vector2 direction;
    private float speed;
    private int damage;
    private boolean isActive;
    protected boolean isEnemyProjectile;

    public Projectile(Vector2 position, Vector2 direction, float speed, int damage, boolean isEnemyProjectile) {
        this.position = position.cpy();
        this.direction = direction.cpy().nor(); // Normaliser la direction pour éviter les vitesses inconsistantes
        this.speed = speed;
        this.damage = damage;
        this.isEnemyProjectile = isEnemyProjectile;

        this.isActive = true;
    }

    public void update() {
        if (!isActive) return;
        position.add(direction.x * speed, direction.y * speed);
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (!isActive) return;

        shapeRenderer.setColor(1, 0, 0, 1); // Rouge
        int segments = 16; // Augmente le nombre de segments pour un meilleur rendu
        shapeRenderer.circle(position.x, position.y, 8 / Main.PPM, segments);
    }

    public boolean outOfBounds(OrthographicCamera camera) {
        float margin = 5.0f; // Supprimer si à plus de 5 unités de la vue
        float cameraLeftBound = camera.position.x - camera.viewportWidth / 2 - margin;
        float cameraRightBound = camera.position.x + camera.viewportWidth / 2 + margin;
        float cameraBottomBound = camera.position.y - camera.viewportHeight / 2;
        float cameraTopBound = camera.position.y + camera.viewportHeight / 2;

        return position.x < cameraLeftBound || position.x > cameraRightBound ||
            position.y < cameraBottomBound || position.y > cameraTopBound;
    }

    // Getter
    public boolean isActive() {
        return isActive;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isEnemyProjectile() {
        return isEnemyProjectile;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public Vector2 getPosition() {
        return position;
    }

}
