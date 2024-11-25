package com.nsm_X.Unit.UnitNPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitInterface;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;

// Création de la class abstraite des ennemies

public abstract class Enemy extends Sprite implements UnitInterface{

    // Attribut
    protected int hp;
    protected int maxHp;
    protected float speed;
    protected boolean jump = false;
    protected float jumpSpeed;

    protected int mobDamage;
    protected float mobGcd;
    protected float mobRange;
    protected float mobFightRange;
    protected MovementStrategy movementStrategy;

    protected World world;
    protected Body body;
    protected Texture texture;
    protected ShapeRenderer shapeRenderer;

    private long lastAttackTime = 0;

    // Champs pour les animations
    protected Animation<TextureRegion> walkLeftAnimation;
    protected Animation<TextureRegion> walkRightAnimation;
    protected Animation<TextureRegion> attackLeftAnimation;
    protected Animation<TextureRegion> attackRightAnimation;

    protected float stateTime; // Temps d'animation global
    protected float attackStateTime; // Temps d'animation pour l'attaque
    protected boolean isAttacking; // Indique si l'ennemi est en train d'attaquer
    protected TextureRegion currentFrame; // Frame actuelle à afficher
    private boolean facingRight = true;

    // Constructeur
    public Enemy(World world, float x, float y){
        this.world = world;
        shapeRenderer = new ShapeRenderer();

        createBody(x, y);
    }

    // Method
    public void createBody(float x, float y){
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;

        bodyDef.position.set(x, y);

        // Create our body in the world using our body definition
        this.body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        PolygonShape shape = new PolygonShape();
        // Taille HIT-BOX
        shape.setAsBox(20/Main.PPM, 20/Main.PPM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void updateMovement(Vector2 playerPos) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        float delta = Gdx.graphics.getDeltaTime(); // Temps écoulé
        stateTime += delta;

        if (isAttacking) {
            // Gestion de l'attaque
            attackStateTime += delta;

            // Animation d'attaque selon la direction actuelle
            currentFrame = facingRight ? attackRightAnimation.getKeyFrame(attackStateTime)
                : attackLeftAnimation.getKeyFrame(attackStateTime);

            // Fin de l'attaque si l'animation est terminée
            if (attackRightAnimation.isAnimationFinished(attackStateTime) ||
                attackLeftAnimation.isAnimationFinished(attackStateTime)) {
                isAttacking = false;
            }
        } else {
            // Gestion des déplacements
            if (isPlayerInRange(playerPos)) {
                // Mouvement vers le joueur
                movementStrategy.move(getBody(), playerPos, speed);

                // Détermine la direction selon la position du joueur
                facingRight = playerPos.x > body.getPosition().x;

                // Animation selon la direction
                currentFrame = facingRight ? walkRightAnimation.getKeyFrame(stateTime, true)
                    : walkLeftAnimation.getKeyFrame(stateTime, true);
            } else {
                // Si le joueur est hors de portée, l'ennemi s'arrête
                body.setLinearVelocity(0, body.getLinearVelocity().y);

                // Animation immobile selon la direction actuelle
                currentFrame = facingRight ? walkRightAnimation.getKeyFrame(0)
                    : walkLeftAnimation.getKeyFrame(0);
            }
        }

        // Applique la frame actuelle à la texture du sprite
        setRegion(currentFrame);
    }

    public boolean isPlayerInRange(Vector2 playerPos){
        float distance = getBody().getPosition().dst(playerPos);
        return distance <= mobRange;
    }

    public boolean canAttack() {
        long currentTime = TimeUtils.millis();
        if ((currentTime - lastAttackTime) >= (mobGcd * 1000)) {
            lastAttackTime = currentTime; // Réinitialise le dernier temps d'attaque
            return true; // Cooldown écoulé, prêt pour attaquer
        }
        return false;
    }

    public void decreaseHealth(int amount){
        this.hp -= amount;
        if(this.hp <= 0){
            this.hp=0;
        }
    }

    public void drawHealthBar(SpriteBatch batch) {
        // Arrêter le batch pour éviter les conflits
        batch.end();

        // Configurer le ShapeRenderer
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Dessiner la barre de vie avec ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Calculer la largeur de la barre en fonction de la santé actuelle
        float barWidth = (float) this.hp / this.maxHp * 40;  // La largeur maximale de la barre est de 40 pixels
        float barHeight = 5 / Main.PPM;  // Hauteur de la barre de vie
        float barX = body.getPosition().x - 20 / Main.PPM;  // Position X de la barre
        float barY = body.getPosition().y + getHeight() / 2 + 0.1f;  // Position Y au-dessus du sprite

        // Dessiner le fond de la barre (noir)
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(barX, barY, 40 / Main.PPM, barHeight);

        // Dessiner la barre de vie (rouge)
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth / Main.PPM, barHeight);

        shapeRenderer.end();

        // Reprendre le batch
        batch.begin();
    }

    public void draw(SpriteBatch batch) {
        if (body != null) { // Rendre uniquement si le body existe
            super.draw(batch);
        }
    }

    public void dispose() {
        texture.dispose(); // Libérer la mémoire de la texture quand elle n'est plus utilisée
    }

    // Getter
    public int getHp(){
        return this.hp;
    }

    public float getSpeed(){
        return this.speed;
    }

    public boolean isJump(){
        return this.jump;
    }

    public float getJumpSpeed(){
        return this.jumpSpeed;
    }

    public int getDamage(){
        return this.mobDamage;
    }

    public float getGcd(){
        return this.mobGcd;
    }

    public float getFightRange(){
        return this.mobFightRange;
    }

    public float getRange(){
        return this.mobRange;
    }

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

    // Nullifier la texture si mort
    public void setNullTexture() {
        this.texture = null;
    }

    public void isIsAttacking(boolean isAttacking) { this.isAttacking = isAttacking; }

    public void setAttackStateTime(float attackStateTime) { this.attackStateTime = attackStateTime; }

    public void isFacingRight(boolean isFacingRight) {this.facingRight = isFacingRight; }
}
