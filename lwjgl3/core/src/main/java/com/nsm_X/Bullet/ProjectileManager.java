package com.nsm_X.Bullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nsm_X.Unit.Unit;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public class ProjectileManager {
    private List<Projectile> activeProjectiles;
    private float screenWidth;
    private float screenHeight;

    public ProjectileManager(float screenWidth, float screenHeight) {
        this.activeProjectiles = new ArrayList<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void addProjectile(Projectile projectile) {
        activeProjectiles.add(projectile);
    }

    public void update() {
        Iterator<Projectile> iterator = activeProjectiles.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            if (projectile.isOutOfBounds(screenWidth, screenHeight)) {
                iterator.remove();
            }
        }
    }

    // public void checkCollisions(Player player, List<Enemy> enemies) {
    //     Iterator<Projectile> iterator = activeProjectiles.iterator();

    //     while (iterator.hasNext()) {
    //         Projectile projectile = iterator.next();

    //         if (projectile.isEnemyProjectile() && projectile.checkCollision(player)) {
    //             player.setHp(player.getHp() - projectile.getDamage());
    //             iterator.remove();
    //             System.out.println("Le joueur a été touché ! HP restant : " + player.getHp());
    //         } else if (!projectile.isEnemyProjectile()) {
    //             for (Enemy enemy : enemies) {
    //                 if (projectile.checkCollision(enemy)) {
    //                     enemy.takeDamage(projectile.getDamage());
    //                     iterator.remove();
    //                     System.out.println("Un ennemi a été touché !");
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    // }

    public void renderProjectiles(SpriteBatch batch) {
        for (Projectile projectile : activeProjectiles) {
            projectile.render(batch);
        }
    }
}