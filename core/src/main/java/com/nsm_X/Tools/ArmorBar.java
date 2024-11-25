package com.nsm_X.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.UnitManager;

public class ArmorBar {


	private UnitManager unitManager;

	private ShapeRenderer shapeRenderer;
    private float maxArmor ;
    private float armor;
    private Player player;
    private Enemy enemy;
    private float x ;
    private float y ;
    private float width ;
    private float height ;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private Main main;
    private final Color armorColor = new Color(0.4f, 0.7f, 1, 1); // Couleur de la barre d'armure



	public ArmorBar(Main main, float x, float y, float width, float height, float maxHp, UnitManager unitManager) {
		this.main = main;
        this.unitManager = unitManager;
		 player = main.getPlayerManager().getPlayer();
		 enemy = unitManager.getEnemy();
		 this.shapeRenderer = new ShapeRenderer();
		 this.maxArmor = player.getMaxArmor();
		 this.armor = player.getHp();
		 this.x = x;
		 this.y = y;
	     this.width = width;
	     this.height = height;
	     this.spriteBatch = new SpriteBatch();
	     this.font = new BitmapFont();
	     this.font.setColor(Color.WHITE);
	}

	public void setHealth(float armor) {
        this.armor = Math.max(0, Math.min(armor, armor)); // Clamp entre 0 et maxArmor
    }

	    public void render() {
	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

	        // Barre de fond (noire)
	        shapeRenderer.setColor(Color.BLACK);
	        shapeRenderer.rect(x,y, width, height);


	        float healthWidth = (player.getArmor()/ maxArmor) * width;
	        shapeRenderer.setColor(armorColor) ;
	        shapeRenderer.rect(x, y, healthWidth, height);
	        shapeRenderer.end();
	    }

	    public void dispose() {
	    	shapeRenderer.dispose();
	        spriteBatch.dispose();
	        font.dispose();
	    }


		public float getX() {
			return x;
		}


		public void setX(float x) {
			this.x = x;
		}


		public float getY() {
			return y;
		}


		public void setY(float y) {
			this.y = y;
		}





}
