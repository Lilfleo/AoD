package com.nsm_X.Combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nsm_X.Bullet.CasterBullet;
import com.nsm_X.Bullet.Projectile;
import com.nsm_X.Bullet.ProjectileManager;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitNPC.NpcCaster;
import com.nsm_X.Unit.UnitPC.Player;

public class FichtingArea {
    private Player player;
    // List des ennemy de la carte
    private List<Enemy> enemies;
    // Projectile
    private List<Projectile> projectiles;  

    public FichtingArea(Player player, List<Enemy> enemies){
        this.player = player;
        this.enemies = enemies;
        this.projectiles = new ArrayList<>();
    }

    public void updateFighting(SpriteBatch batch) {
        Vector2 playerPosition = player.getBody().getPosition();
        // Parcourir la liste d'ennemi
        for (Enemy enemy : enemies) {
            if (isInFightRange(playerPosition, enemy) && enemy.canAttack()) {
                if (enemy instanceof NpcCaster) {
                    // Créer un projectile et le diriger vers le joueur
                    Vector2 enemyPosition = enemy.getBody().getPosition();
                    Vector2 direction = playerPosition.cpy().sub(enemyPosition).nor(); // Calcule la direction vers le joueur

                    // Crée une instance de CasterBullet avec direction
                    Projectile projectile = new CasterBullet(enemyPosition.cpy(), direction);

                    // Ajouter le projectile au ProjectileManager pour gestion
                    projectiles.add(projectile);
                } else {
                    // Pour les ennemis corps-à-corps
                    int newHp = player.getHp() - enemy.getmobDamage();
                    System.out.println("HP du joueur: " + newHp);
                    player.setHp(newHp);
                }
            }
        }
        updateProjectiles();
        renderProjectiles(batch);
    }

    // Vérifie si le joueur est dans la portée de combat de l'ennemi
    private boolean isInFightRange(Vector2 playerPosition, Enemy enemy) {
        float distance = enemy.getBody().getPosition().dst(playerPosition);
        return distance <= enemy.getMobFightRange();
    }

    private void updateProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            // Vérifie si le projectile sort des limites de l'écran
            if (projectile.isOutOfBounds(800, 600)) {  // Remplace avec les dimensions de ton écran
                iterator.remove();
            }
        }
    }

    // Dessine les projectiles sur l'écran
    private void renderProjectiles(SpriteBatch batch) {
        batch.begin();
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }
        batch.end();
    }


}
