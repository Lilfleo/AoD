package com.nsm_X.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nsm_X.Main;

public class Hud implements Disposable {
	
    public Stage stage;
	private Viewport viewport;
	
	public Hud (SpriteBatch sb) {
		
		
		viewport = new FitViewport(Main.V_Width / Main.PPM, Main.V_HEIGHT / Main.PPM, new OrthographicCamera());
		stage = new Stage(viewport, sb);
	}

	public void dispose() {
		stage.dispose();
		
	}
	
}
