package com.nsm_X;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldManager {

    // Attribut
    private World world;
    private Body ground;
    
    // Getter
    public World getWorld(){
        return world;
    }

    public Body getGround(){
        return this.ground;
    }

    // Constructor
    public WorldManager(){
        world = new World(new Vector2(0, -250f), true);
        createGround();
    }

    // Method

    private void createGround(){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, -5);

        ground = world.createBody(groundBodyDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(600, 7.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.0f;

        ground.createFixture(fixtureDef);
        groundShape.dispose();
    }
    
    public void stepWorld(){
        world.step(1/60f, 6, 2);
    }
    
    public void dispose(){
        world.dispose();
    }
}
