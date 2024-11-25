package com.nsm_X;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Screen.StartScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public static final float PPM = 100;
    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;

    private UnitManager unitManager;
    private PlayerManager playerManager;
    private World world;// Remplace Player par UnitManager
    private int floorCounter;

    public int getFloorCounter() {
        return floorCounter;
    }
    public void incrementFloorCounter() {
        this.floorCounter++;
    }

    public void setFloorCounter(int floorCounter) {
        this.floorCounter = floorCounter;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        playerManager = new PlayerManager(this);

        setScreen(new StartScreen(this));  // Passe UnitManager au lieu de Player
    }
    public PlayerManager getPlayerManager() {return playerManager;}

    public World getWorld() {return world;}

    public UnitManager getUnitManager() {
        return unitManager;  // Retourner UnitManager pour l'accès au joueur
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        world.dispose();
    }
}
