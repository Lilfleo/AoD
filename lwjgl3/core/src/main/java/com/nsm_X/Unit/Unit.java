package com.nsm_X.Unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.screen.MainPlayScreen;

public abstract class Unit extends Sprite{
    protected int hp;
    protected int ap;
    protected float speed;
    protected boolean jump = false;
    protected float jumpSpeed;

    protected World world;
    protected Body body;
    protected Texture texture;

   

    public Unit(World world, MainPlayScreen screen) {
    	this.world = world;
	}

	public abstract void createBody();

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
    
}