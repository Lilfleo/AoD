package com.nsm_X.Unit.UnitPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nsm_X.Main;
import com.nsm_X.Item.ItemData;
import com.nsm_X.Unit.Unit;
import com.nsm_X.Unit.UnitInterface;
import com.nsm_X.screen.MainPlayScreen;

public class Player extends Unit {

	private Texture texture;
	public Body body;
	private FixtureDef fixtureDef;
	


	// Constructeur

	public Player(World world , MainPlayScreen screen) {
		super(world, screen);
		 this.hp = 100;
	     this.ap = 200;
	     this.speed = 100;
	     this.jumpSpeed = 5;
	    
		
		texture = new Texture("player01.png");
		setBounds(0, 0, 32/Main.PPM, 32/Main.PPM);
		setRegion(texture);
		
		createBody();
	}

	// Method
 
	public void update (float dt) {
		setPosition(body.getPosition().x- getWidth()/2,body.getPosition().y- getHeight()/2);
    	
    }

	public void createBody() {

		// First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(64/ Main.PPM, 129/ Main.PPM);
        body = world.createBody(bodyDef);

         
        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ Main.PPM);
        
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
	}
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void movement() {
		float delta = Gdx.graphics.getDeltaTime();
        // Mouvement vers la gauche
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.setLinearVelocity(-this.speed*delta, body.getLinearVelocity().y);
        }
        // Mouvement vers la droite
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(this.speed*delta, body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        // Jump
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.jump) {
            // body.setLinearVelocity(body.getLinearVelocity().x, jumpSpeed*999999999);
            body.applyLinearImpulse(new Vector2(0, jumpSpeed), body.getWorldCenter(), true);
            // body.applyLinearImpulse(0, this.jumpSpeed*99999999, body.getWorldCenter().x, body.getWorldCenter().y, true); // Impulsion pour un coup de pouce
            this.jump = true; // Empêche le saut multiple
        }

        //
        if (body.getLinearVelocity().y == 0) {
            this.jump = false;
        }
		
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Body getBody(Body body) {
		this.body = body;
		return null;
	}
	
	

	
}
