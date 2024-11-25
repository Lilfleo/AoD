package com.nsm_X;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Bullet.ProjectileManager;
import com.nsm_X.Combat.FichtingArea;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitNPC.NpcCac;
import com.nsm_X.Unit.UnitNPC.NpcCaster;
import com.nsm_X.Unit.UnitNPC.Movement.MoveApproachPattern;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.screen.MainPlayScreen;

public class UnitManager {
    private Player player;
    private Enemy enemy;
    private List<Enemy> enemies = new ArrayList<>();
    private SpriteBatch batch;
    private OrthographicCamera gameCamera;


    public Player getPlayer(){
        return this.player;
    }

    public Enemy getEnemy(){
        return this.enemy;
    }

    public UnitManager (World world, MainPlayScreen screen){
        
        // Création du joueur
        player = new Player(world, null);

//        // Stratégie de movement
//        MovementStrategy pattern1 = new MoveApproachPattern();
//        enemy = new NpcCac(world, pattern1);
//        enemy.setPosition(700, 100);
//
//        // Création d'une liste d'ennemie
//        enemies.add(enemy);
//
//        // 
        batch = new SpriteBatch();

    }
    
    public void update(float dt) {
    	player.update(dt);
    }

    public void updateUnit(){
        player.movement();

//        Vector2 playerPosition = player.getPosition();
//        enemy.updateMovement(playerPosition);
//
//        // Fighting area
//        FichtingArea fightArea = new FichtingArea(player, enemies);
//        fightArea.updateFighting(batch);

    }

    public void render() {
//        batch.begin();
//    
//        // Rendu du joueur
////        if (player.getTexture() != null) {
////            float playerWidth = 40;
////            float playerHeight = 40;
////    
////            batch.draw(
////                player.getTexture(),
////                player.getBody().getPosition().x * Main.PPM - playerWidth / 2,
////                player.getBody().getPosition().y * Main.PPM - playerHeight / 2,
////                playerWidth,
////                playerHeight
////            );
////        }
//    
////        // Rendu de l'ennemi
////        if (enemy.getTexture() != null) {
////            float enemyWidth = enemy.getTexture().getWidth() / Main.PPM;
////            float enemyHeight = enemy.getTexture().getHeight() / Main.PPM;
////    
////            batch.draw(
////                enemy.getTexture(),
////                enemy.getBody().getPosition().x * Main.PPM - enemyWidth / 2,
////                enemy.getBody().getPosition().y * Main.PPM - enemyHeight / 2,
////                enemyWidth,
////                enemyHeight
////            );
////        }
//    
//        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

}
