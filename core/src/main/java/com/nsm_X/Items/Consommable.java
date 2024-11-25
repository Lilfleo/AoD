package com.nsm_X.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitPC.Player;

import java.awt.*;

public abstract class Consommable extends Sprite {
    //Position
    protected float x;
    protected float y;

    protected World world;
    protected Body body;
    protected Texture texture;
    protected int amount;

    public Consommable(World world, float x, float y) {
        this.world = world;

        createBody(x, y);
    }

    public void createBody(float x, float y) {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x, y);

        // Create our body in the world using our body definition
        this.body = world.createBody(bodyDef);

        this.body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        // Taille HIT-BOX
        shape.setAsBox(10/Main.PPM, 10/Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true; // Rend intangible

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void update() {
        if (texture != null) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    //Method
    public void increaseAmount(int newAmount) {
        this.amount = newAmount;
    }

    public abstract void applyEffect(Player player);

    //GETTER

    public Texture getTexture() { return this.texture; }

    public int getAmount() { return this.amount; }

    public Body getBody() { return this.body; }

    //SETTER

    public void dispose() {
        if (texture != null) {
            texture.dispose(); // Libère la mémoire de la texture
        }
    }
}
