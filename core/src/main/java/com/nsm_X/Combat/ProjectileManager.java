package com.nsm_X.Combat;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nsm_X.Projectile.Projectile;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileManager {

    private List<Projectile> projectiles;
    private Player player;
    private ShapeRenderer shapeRenderer;

    public ProjectileManager(Player player) {
        this.projectiles = new ArrayList<>();
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void update(OrthographicCamera gameCamera) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            // Vérifie les collisions avec le joueur
            if (projectile.isActive() && isCollidingWithPlayer(projectile)) {
                player.decreaseHp(projectile.getDamage());
                projectile.deactivate();
                iterator.remove();
                continue; // Passe au prochain projectile
            }

            // Vérifie si le projectile sort de l'écran
            if (projectile.outOfBounds(gameCamera)) {
                projectile.deactivate();
                iterator.remove();
            }
        }
    }

    private boolean isCollidingWithPlayer(Projectile projectile) {
        float distance = player.getBody().getPosition().dst(projectile.getPosition());
        return distance < 20 / Main.PPM; // Distance de collision réaliste
    }

    public void render(ShapeRenderer shapeRenderer) {
        // ShapeRenderer doit utiliser la matrice de la caméra
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Projectile projectile : projectiles) {
            projectile.render(shapeRenderer); // Les positions sont dans le système de coordonnées Box2D
        }
        shapeRenderer.end();
    }
}
