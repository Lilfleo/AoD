package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nsm_X.Items.ItemFactory;
import com.nsm_X.Main;
import com.nsm_X.Tools.*;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.UnitManager;

import java.util.ArrayList;
import java.util.List;

public class MainPlayScreen implements Screen {
    private static final float LIMITE_INFERIEURE = -1f;
    private final Main main;

    // Caméra et viewport pour le jeu
    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    // Carte Tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapManager mapManager;

    // Monde Box2D et rendu de débogage
    private World world;
    private Box2DDebugRenderer debugRenderer;

    // Gestion des unités (joueur et ennemi)
    private UnitManager unitManager;
    private Player player;
    private WorldContactListener worldContactListener;

    // Gestion de la barre de vie et d'armure
    private ArmorBar armorBar;
    private HealthBar healthBar;

    private Option gameUI;
    private List<Body> bodiesToDestroy = new ArrayList<>();

    private ItemFactory itemFactory;
    private Label notificationLabel;// Nouveau membre pour le label de notification
    private Label healthStatLabel;
    private Label armorStatLabel;
    private Label floorLabel;


    public MainPlayScreen(Main main) {
        this.main = main;
        player = main.getPlayerManager().getPlayer();

        // Initialisation de la caméra et du viewport
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(Main.V_WIDTH / Main.PPM, Main.V_HEIGHT / Main.PPM, gameCamera);

        // Chargement et rendu de la carte Tiled
        mapLoader = new TmxMapLoader();
        mapManager = new MapManager();
        if (main.getFloorCounter() == 0) {
            map = mapLoader.load("map/Map00.tmx");
        } else {
            map = mapLoader.load(mapManager.randomMap());
        }
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM);

        // Position initiale de la caméra
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Création du monde Box2D et du débogueur Box2D
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        //WorldContactListener
        worldContactListener = new WorldContactListener(main, this);

        // Gestion des unités (création du joueur et des ennemis)
        unitManager = new UnitManager(world, main, map, this);

        // Création temporaire d'une potion
        itemFactory = new ItemFactory(main);
        itemFactory.createConsommableSpawnZone(map, world);

        // Création des objets du monde à partir de la carte Tiled
        new WorldCreator(world, map, main, unitManager, worldContactListener);

        gameUI = new Option(main);

        player.createBody(world, 64, 130);

    }

    public void markBodyForDestruction(Body body) {
        bodiesToDestroy.add(body);
    }

    private void update() {

        //World Step
        world.step(1 / 60f, 6, 2);

        //Update Unit
        unitManager.updateUnit(gameCamera);

        itemFactory.update();

        //Mouvement
        player.movement();

        //Destruction des bodies
        destroyPendingBodies();

        healthStatLabel.setText("HEALTH: " + player.getHp());
        armorStatLabel.setText("ARMOR: " + player.getArmor());

        //Camera
        gameCamera.position.x = player.getBody().getPosition().x;
        // Récupérer les dimensions de la carte en unités Box2D
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class) / Main.PPM;
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class) / Main.PPM;
        // Calculer les bornes pour limiter la caméra
        float cameraHalfWidth = gamePort.getWorldWidth() / 2;
        float cameraHalfHeight = gamePort.getWorldHeight() / 2;

        float minX = cameraHalfWidth;
        float maxX = mapWidth - cameraHalfWidth;
        float minY = cameraHalfHeight;
        float maxY = mapHeight - cameraHalfHeight;

        // Appliquer les limites pour la position de la caméra
        gameCamera.position.x = Math.max(minX, Math.min(gameCamera.position.x, maxX));
        gameCamera.position.y = Math.max(minY, Math.min(gameCamera.position.y, maxY));

        gameCamera.update();
        // Définir la vue de la carte en fonction de la caméra
        mapRenderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        // Mise à jour de l'état du jeu
        update();

        // Effacer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rendu de la carte Tiled
        mapRenderer.render();

        // Unit render
        main.batch.setProjectionMatrix(gameCamera.combined);
        main.shapeRenderer.setProjectionMatrix(gameCamera.combined);
        unitManager.render();
        itemFactory.render(main.batch);

        // Consommable
        main.batch.begin();
        player.draw(main.batch);
        main.batch.end();

        // Rendu des barres de vie et d'armure
        armorBar.render();
        healthBar.render();


        // Gestion et rendu de l'interface utilisateur
        handleNotifications(); // Mise à jour des notifications
        gameUI.getStage().act(delta); // Mise à jour des actions du Stage
        gameUI.getStage().draw();     // Dessin des éléments du Stage

        // Rendu du débogueur Box2D
        //debugRenderer.render(world, gameCamera.combined);


        if (player.getPosition().y < LIMITE_INFERIEURE) {
            HighscoreManager.saveHighscore(main.getFloorCounter());
            player.deathReset();
            main.setScreen(new FallOverScreen(main));
        }

        if (player.getHp()==0) {
            HighscoreManager.saveHighscore(main.getFloorCounter());
            player.deathReset();
            main.setScreen(new GameOverScreen(this.main));
        }
    }



    private void destroyPendingBodies() {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
        }
        bodiesToDestroy.clear(); // Vide la liste après suppression
    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
        gameCamera.update();  // Mettre à jour la caméra après avoir redimensionné le viewport
    }
    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Création des barres de vie et d'armure
        healthBar = new HealthBar(this.main, 850, screenHeight - 40, 200, 15, player.getMaxHp(), unitManager);
        armorBar = new ArmorBar(this.main, 850, screenHeight - 40, 200, 20, player.getArmor(), unitManager);


        // Création du label de notification
        notificationLabel = new Label("", gameUI.skin);
        notificationLabel.setVisible(false);
        notificationLabel.setPosition((screenWidth - notificationLabel.getPrefWidth()) / 2, (screenHeight - notificationLabel.getPrefHeight()) / 2);
        gameUI.getStage().addActor(notificationLabel); // Ajout du label au Stage


         healthStatLabel = new Label("HEALTH: " + player.getHp(), gameUI.skin);
         armorStatLabel = new Label("ARMOR: " + player.getArmor(), gameUI.skin);

        healthStatLabel.setPosition(screenWidth - healthStatLabel.getPrefWidth() - 10, screenHeight - 60);  // Coin supérieur droit
        armorStatLabel.setPosition(screenWidth - armorStatLabel.getPrefWidth() - 10, screenHeight - 20);  // Juste sous le HP


        gameUI.getStage().addActor(armorStatLabel);
        gameUI.getStage().addActor(healthStatLabel);

        floorLabel = new Label("FLOOR: " + main.getFloorCounter(), gameUI.skin);
        floorLabel.setPosition(screenWidth - armorStatLabel.getPrefWidth() -10, screenHeight - 700);  // Coin supérieur gauche
        gameUI.getStage().addActor(floorLabel);

        Gdx.input.setInputProcessor(gameUI.getStage());
        Gdx.input.setInputProcessor(gameUI.getStage()); // Assurer que le Stage est utilisé comme InputProcessor
    }

    private void handleNotifications() {
        if (NotificationManager.isNotificationActive()) {
            String message = NotificationManager.getLastNotification();
            notificationLabel.setText(message);
            notificationLabel.setVisible(true);

            // Réinitialiser les animations pour éviter des conflits
            notificationLabel.clearActions();
            notificationLabel.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(0.5f),
                Actions.delay(1.5f),
                Actions.fadeOut(0.5f),
                Actions.run(() -> notificationLabel.setVisible(false))
            ));

            NotificationManager.resetNotification(); // Réinitialiser l'état de la notification
        }
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        gameUI.music.stop();
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
//        world.dispose();
        unitManager.dispose();
        debugRenderer.dispose();
        healthBar.dispose();
        armorBar.dispose();
        main.dispose();
    }
}


