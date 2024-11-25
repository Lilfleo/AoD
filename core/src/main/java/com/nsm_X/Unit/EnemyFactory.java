package com.nsm_X.Unit;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitNPC.Movement.MoveAvoidPattern;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;
import com.nsm_X.Unit.UnitNPC.Movement.MoveApproachPattern;
import com.nsm_X.Unit.UnitNPC.NpcCaster;
import com.nsm_X.Unit.UnitNPC.NpcCac;
import com.nsm_X.Unit.UnitNPC.NpcTank;

public class EnemyFactory {
    private List<Enemy> enemies = new ArrayList<>();
    private Main main;

    public EnemyFactory(Main main){
        this.main = main;
    }

    public List<Enemy> createEnemies(World world, float centerX, float centerY) {

        MovementStrategy pattern1 = new MoveApproachPattern();
        MovementStrategy pattern2 = new MoveAvoidPattern();

        int casterCount;
        int tankCount;
        int cacCount;
        float count = main.getFloorCounter() * 0.1f;

        if (main.getFloorCounter() == 0){
            casterCount = 0;
            tankCount = 0;
            cacCount = 1;
        }else if (main.getFloorCounter() < 5){
            casterCount = MathUtils.random(0, 1); // Entre 1 et 3 casters
            tankCount = MathUtils.random(0, 1);  // Entre 1 et 2 tanks
            cacCount = MathUtils.random(0, 2);   // Entre 2 et 4 CAC
        }else{
            casterCount = MathUtils.random(0, 1 + (int)count); // Entre 1 et 3 casters
            tankCount = MathUtils.random(0, 2 + (int)count);  // Entre 1 et 2 tanks
            cacCount = MathUtils.random(0, 3 + (int)count);   // Entre 2 et 4 CAC
        }

        // Générer les casters
        for (int i = 0; i < casterCount; i++) {
            float offsetX = MathUtils.random(-1.0f, 1.0f);
            enemies.add(new NpcCaster(world, pattern2, centerX + offsetX, centerY));
        }

        // Générer les tanks
        for (int i = 0; i < tankCount; i++) {
            float offsetX = MathUtils.random(-1.0f, 1.0f);
            enemies.add(new NpcTank(world, pattern1, centerX + offsetX, centerY));
        }

        // Générer les ennemis CAC
        for (int i = 0; i < cacCount; i++) {
            float offsetX = MathUtils.random(-1.0f, 1.0f);
            enemies.add(new NpcCac(world, pattern1, centerX + offsetX, centerY));
        }

        return enemies;
    }

    public void updateEnemiesMovement(Vector2 playerPosition) {
        for (Enemy enemy : enemies) {
            enemy.updateMovement(playerPosition); // Met à jour chaque ennemi avec la position du joueur
        }
    }

    public void render(SpriteBatch batch){
        for (Enemy enemy : enemies) {
            if (enemy.getTexture() != null) {
                enemy.draw(batch); // S'assurer que chaque ennemi est dessiné avec sa texture
                enemy.drawHealthBar(batch); // Dessiner la barre de vie
            }
        }
    }
}

