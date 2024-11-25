package com.nsm_X;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Combat.FightingArea;
import com.nsm_X.Combat.ProjectileManager;
import com.nsm_X.Screen.MainPlayScreen;
import com.nsm_X.Unit.EnemyFactory;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public class UnitManager {
    //Unit
    private Player player;
    private Enemy enemy;
    private List<Enemy> enemies = new ArrayList<>();

    private SpriteBatch batch;
    private World world;
    private Main main;
    private FightingArea fightArea;
    private ProjectileManager projectileManager;
    private EnemyFactory enemyFactory;
    private MainPlayScreen mainPlayScreen;

    public Enemy getEnemy() {
        return this.enemy;
    }

    public UnitManager(World world, Main main, TiledMap map, MainPlayScreen mainPlayScreen) {
        this.mainPlayScreen = mainPlayScreen;
        this.world = world;
        this.main = main;
        this.batch = new SpriteBatch();
        player = main.getPlayerManager().getPlayer();

        // Création de la factory pour les ennemis
        enemyFactory = new EnemyFactory(main);

        // Création des ennemis depuis les zones d'apparition dans Tiled
        createEnemySpawnZones(map, world);

        // Création du gestionnaire de projectiles
        projectileManager = new ProjectileManager(player);

        // Création du FightingArea
        fightArea = new FightingArea(main, world, player, enemies, projectileManager, mainPlayScreen);

    }

    public void createEnemySpawnZones(TiledMap map, World world) {
        MapLayer enemyLayer = map.getLayers().get("spawn_monstre");
        if (enemyLayer == null) {
            throw new IllegalStateException("Aucune couche 'enemy_spawn' trouvée dans la carte.");
        }

        for (MapObject object : enemyLayer.getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            float centerX = (rect.getX() + rect.getWidth() / 2) / Main.PPM;
            float centerY = (rect.getY() + rect.getHeight() / 2) / Main.PPM;

            // Génère les ennemis de chaque type
            List<Enemy> spawnedEnemies = enemyFactory.createEnemies(world, centerX, centerY);

            // Ajoute les ennemis générés à la liste globale
            enemies.addAll(spawnedEnemies);
        }
    }

    public void updateUnit(OrthographicCamera gameCamera) {
        //player.movement();

        Vector2 playerPosition = player.getPosition();
        // Met à jour tous les ennemis via la factory
        enemyFactory.updateEnemiesMovement(playerPosition);

        // Mise à jour de la zone de combat
        fightArea.updateFighting(gameCamera);

    }

    public void render() {
        main.batch.begin();

        enemyFactory.render(main.batch);
        main.batch.end(); // Fin du dessin des éléments graphiques

        fightArea.render(main.shapeRenderer);
    }

    public void dispose() {
        batch.dispose();
    }

}
