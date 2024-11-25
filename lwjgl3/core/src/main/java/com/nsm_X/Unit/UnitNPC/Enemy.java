package com.nsm_X.Unit.UnitNPC;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.nsm_X.Main;
import com.nsm_X.Unit.Unit;
import com.nsm_X.Unit.UnitInterface;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;

// Création de la class abstraite des ennemies

public class Enemy extends Unit implements UnitInterface{

    // Attribut 
    protected int mobDamage;
    protected float mobGcd;
    protected float mobRange;
    protected float mobFightRange;
    protected MovementStrategy movementStrategy;

    // Ajout de lastAttackTime pour gérer le cooldown
    private long lastAttackTime = 0;

    // Getter

    public Body getBody(){
        return this.body;
    }

    public int getmobDamage(){
        return this.mobDamage;
    }

    public float getMobGcd(){
        return this.mobGcd;
    }

    public float getMobRange(){
        return this.mobRange;
    }

    public float getMobFightRange(){
        return this.mobFightRange;
    }

    public Texture getTexture(){
        return this.texture;
    }
    
    // Constructeur

    public Enemy(World world){
        super(world);

        createBody();
    }

    // Method

    public void createBody(){
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world

        // Create our body in the world using our body definition
        this.setBody(world.createBody(bodyDef));

        // Create a circle shape and set its radius to 6
        CircleShape shape = new CircleShape();
        shape.setRadius(16);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        // Fixture fixture = mobBody.createFixture(fixtureDef);

        this.getBody().createFixture(fixtureDef);
        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();
    }

    // Set la position dans une method a part pour les rendre aléatoire

    public void setPosition(float x, float y) {
        if (getBody() != null) {
            getBody().setTransform(x, y, getBody().getAngle());
        }
    }

    // Method de déplacement si player dans la range

    public void updateMovement(Vector2 playerPos){
        if (isPlayerInRange(playerPos)){
            movementStrategy.move(getBody(), playerPos, speed);
        }else{
            getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
        }
    }

    public boolean isPlayerInRange(Vector2 playerPos){
        float distance = getBody().getPosition().dst(playerPos);
        return distance <= mobRange;
    }

    public boolean isAttackable(Vector2 playerPos){
        if (isPlayerInFightRange(playerPos)){
            return true;
        }
        return false;
    }

    public boolean isPlayerInFightRange(Vector2 playerPos){
        float distance = body.getPosition().dst(playerPos);
        return distance <= mobFightRange;
    }

    public boolean canAttack() {
        long currentTime = TimeUtils.millis();
        if ((currentTime - lastAttackTime) >= (mobGcd * 1000)) {
            lastAttackTime = currentTime; // Réinitialise le dernier temps d'attaque
            return true; // Cooldown écoulé, prêt pour attaquer
        }
        return false;
    }

    public void dispose() {
        texture.dispose(); // Libérer la mémoire de la texture quand elle n'est plus utilisée
    }
}
