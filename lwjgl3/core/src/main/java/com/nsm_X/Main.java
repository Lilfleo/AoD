package com.nsm_X;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nsm_X.screen.MainPlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
	public SpriteBatch batch;
    public static final float PPM = 100;
    public static final int V_Width = 1080;
    public static final int V_HEIGHT = 720;

    @Override
    public void create() {
    	batch = new SpriteBatch();
        setScreen(new MainPlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}

    // private WorldManager worldManager;
    // private UnitManager unitManager;
    // private RenderManager renderManager;

   // @Override
    // public void create() {
    //     worldManager = new WorldManager();
    //     unitManager = new UnitManager(worldManager);
    //     renderManager = new RenderManager();
    // }

    // @Override
    // public void render() {
    //     Gdx.gl.glClearColor(0, 0, 0, 1);
    //     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    //     worldManager.stepWorld();
    //     unitManager.updateUnit();
    //     renderManager.render(worldManager, unitManager);
    // }

    // @Override
    // public void dispose() {
    //     worldManager.dispose();
    //     renderManager.dispose();
    // }
