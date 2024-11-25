package com.nsm_X.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Main;
import com.nsm_X.UnitManager;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public class HealthBar {


	private UnitManager unitManager;

	private ShapeRenderer shapeRenderer;
    private float maxHp ;
    private float hp;
    private Player player;
    private Enemy enemy;
    private float x ;
    private float y ;
    private float width ;
    private float height ;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private Main main;



	public HealthBar(Main main,float x, float y, float width, float height,float maxHp, UnitManager unitManager) {
		this.main = main;
        this.unitManager = unitManager;
		 player = main.getPlayerManager().getPlayer();
		 enemy = unitManager.getEnemy();

		 this.shapeRenderer = new ShapeRenderer();
		 this.maxHp = player.getMaxHp();
		 this.hp = player.getHp();
		 this.x = x;
		 this.y = y;
	     this.width = width;
	     this.height = height;
	     this.spriteBatch = new SpriteBatch();
	     this.font = new BitmapFont();
	     this.font.setColor(Color.WHITE);
	}

	public void setHealth(float health) {
        this.hp = Math.max(0, Math.min(health, maxHp)); // Clamp entre 0 et maxHealth
    }

//	 public void decreaseHealth(float damage){
//		   player.decreaseHealth(enemy.getmobDamage());
//	    }


	    public void render() {
	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

	        // Barre de fond (noire)
	        shapeRenderer.setColor(Color.BLACK);
	        shapeRenderer.rect(x,y, width, height);


	        float healthWidth = (player.getHp()/ maxHp) * width;
	        shapeRenderer.setColor(Color.RED);
	        shapeRenderer.rect(x, y, healthWidth, height);





	        shapeRenderer.end();
	     // affiche les points de vie sous forme de texte
//	        spriteBatch.begin();
//	        String healthText = "PV : "+ (int) player.getHp() + " / " + (int) player.getMaxHp();
//	        font.draw(spriteBatch, healthText, x + width + 10, y + height - 5); // Texte à droite de la barre
//	        spriteBatch.end();
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
