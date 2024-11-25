package com.nsm_X.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.nsm_X.Main;
import com.nsm_X.UnitManager;
import com.nsm_X.Scenes.Hud;
import com.nsm_X.Tools.MapManager;
import com.nsm_X.Tools.WorldCreator;
import com.nsm_X.Unit.UnitPC.Player;

public class MainPlayScreen implements Screen {

    private final Main main;
//    private Player player;
    private UnitManager unitManager;
    private Hud hud;
    private Texture texture;

    // Caméra et viewport pour le jeu
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private SpriteBatch batch;
    
    //map manager
    private static MapManager mapManager;
    

    // Carte Tiled
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Monde Box2D et rendu de débogage
    private World world;
    private Box2DDebugRenderer b2dr;
	private String randommap;
    
    // gestion des events
    

   
   

    public MainPlayScreen(Main main) {
    	
    	
        this.main = main;
        // creation de la caméra 
        gamecam = new OrthographicCamera();
        // creation du viewport
        gamePort = new FitViewport(Main.V_Width/ Main.PPM, Main.V_HEIGHT / Main.PPM, gamecam);
        
        hud = new Hud(main.batch);
      
        // Chargement et rendu de la carte Tiled
        mapManager = new MapManager();
        // generer une nouvelle carte a charger
        randommap = (String) mapManager.randomMap();
        maploader = new TmxMapLoader();
        map = maploader.load(randommap);
        
        
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Main.PPM);
        // Position initiale de la caméra
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        // Création du monde Box2D 
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
      
        new WorldCreator(world, map);
        
        unitManager = new UnitManager(world, null);
        
  
        



       
    }

    public void update(float dt) {
    	unitManager.updateUnit();
        world.step(1/60f, 6, 2);
        
        unitManager.update(dt);
        
        gamecam.position.x = unitManager.getPlayer().body.getPosition().x;
        
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
    
        update(delta);

        // Effacer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
     // Rendu de la carte Tiled
        renderer.render();
 
        // Rendu des objets Box2D en mode débogage (facultatif)
//        b2dr.render(world, gamecam.combined);
        
        main.batch.setProjectionMatrix(gamecam.combined);
        main.batch.begin();
        unitManager.getPlayer().draw(main.batch);
//        player.draw(main.batch);
        main.batch.end();
        
        main.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        
    }
      

    @Override
    public void resize(int width, int height) {
        // Mise à jour du viewport
        gamePort.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Libération des ressources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        unitManager.dispose();
        hud.dispose();
    }
}