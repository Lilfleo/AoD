package com.nsm_X.Combat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Main;
import com.nsm_X.Projectile.CasterProjectile;
import com.nsm_X.Screen.MainPlayScreen;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitNPC.NpcCaster;
import com.nsm_X.Unit.UnitPC.Player;

public class FightingArea {
    private Player player;
    // List des ennemy de la carte
    private List<Enemy> enemies;
    private ProjectileManager projectileManager;
    private World world;
    private Main main;
    private MainPlayScreen mainPlayScreen;
    //Hitbox player
    private float attackBoxWidth = 0.7f; // Largeur de la boîte d'attaque
    private float attackBoxHeight = 1.0f; // Hauteur de la boîte d'attaque
    private Set<Enemy> enemiesHitDuringAttack = new HashSet<>();


    public FightingArea(Main main, World world, Player player, List<Enemy> enemies, ProjectileManager projectileManager, MainPlayScreen mainPlayScreen) {
        this.mainPlayScreen = mainPlayScreen;
        this.projectileManager = projectileManager;
        this.main = main;
        this.world = world;
        this.player = player;
        this.enemies = enemies;
    }

    public void updateFighting(OrthographicCamera gameCamera) {
        Vector2 playerPosition = player.getBody().getPosition();

        // Initialisation
        if (player.getIsAttacking() && player.canAttack()) {
            enemiesHitDuringAttack.clear(); // Réinitialise les ennemis touchés pour cette attaque
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            Vector2 enemyPosition = enemy.getBody().getPosition();

            // Vérifie si l'ennemi est dans la hitbox du joueur
            if (player.getIsAttacking() && isEnemyInAttackBox(playerPosition, enemyPosition, player.isFacingRight())) {
                if (!enemiesHitDuringAttack.contains(enemy)) { // Ne touche que les ennemis non encore affectés
                    enemy.decreaseHealth(player.getCacDamage());
                    enemiesHitDuringAttack.add(enemy); // Marque l'ennemi comme touché

                    // Vérifie si l'ennemi est mort
                    if (enemy.getHp() <= 0) {
                        iterator.remove(); // Suppression sécurisée
                        enemy.setNullTexture(); // Nettoyage du visuel
                        mainPlayScreen.markBodyForDestruction(enemy.getBody());
                    }
                }
            }
        }

        // Gère les attaques des ennemis
        for (Enemy enemy : enemies) {
            if (isInFightRange(playerPosition, enemy) && enemy.canAttack()) {
                enemy.isIsAttacking(true); // Démarre l'animation d'attaque
                enemy.setAttackStateTime(0); // Réinitialise le temps de l'attaque
                enemy.isFacingRight(playerPosition.x > enemy.getBody().getPosition().x); // Définir la direction

                if (enemy instanceof NpcCaster && enemy.getTexture() != null) {
                    // Gestion des projectiles des casters
                    Vector2 enemyPosition = enemy.getBody().getPosition();
                    Vector2 direction = playerPosition.cpy().sub(enemyPosition).nor();
                    Vector2 startPosition = enemyPosition.cpy().add(direction.scl(10 / Main.PPM));
                    CasterProjectile projectile = new CasterProjectile(startPosition, direction);
                    projectileManager.addProjectile(projectile);
                } else {
                    // Pour les ennemis corps-à-corps
                    player.decreaseHp(enemy.getmobDamage());
                }
            }
        }
        // Met à jour et affiche les projectiles
        projectileManager.update(gameCamera);
    }

    public void render(ShapeRenderer shapeRenderer) {
        projectileManager.render(shapeRenderer);
    }

    // Vérifie si le joueur est dans la portée de combat de l'ennemi
    private boolean isInFightRange(Vector2 playerPosition, Enemy enemy) {
        float distance = enemy.getBody().getPosition().dst(playerPosition);
        return distance <= enemy.getMobFightRange();
    }

    private Enemy getNearestEnemy(Vector2 playerPosition, float attackRange) {
        Enemy closestEnemy = null;
        float closestDistance = Float.MAX_VALUE;
        float minimumChangeDistance = 0.05f; // Distance minimale pour déclencher un changement de direction

        for (Enemy enemy : enemies) {
            float distance = enemy.getBody().getPosition().dst(playerPosition);
            if (distance <= attackRange && distance < closestDistance) {
                if (closestDistance - distance > minimumChangeDistance) { // Change uniquement si la différence est significative
                    closestDistance = distance;
                    closestEnemy = enemy;
                }
            }
        }
        return closestEnemy;
    }

    private boolean isEnemyInAttackBox(Vector2 playerPosition, Vector2 enemyPosition, boolean facingRight) {
        float halfHeight = attackBoxHeight / 2;

        // Définir les limites de la hitbox
        float boxLeft = facingRight ? playerPosition.x : playerPosition.x - attackBoxWidth;
        float boxRight = facingRight ? playerPosition.x + attackBoxWidth : playerPosition.x;
        float boxTop = playerPosition.y + halfHeight;
        float boxBottom = playerPosition.y - halfHeight;

        // Vérifier si l'ennemi est dans la boîte
        return enemyPosition.x >= boxLeft && enemyPosition.x <= boxRight &&
                enemyPosition.y >= boxBottom && enemyPosition.y <= boxTop;
    }
}
