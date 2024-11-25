package com.nsm_X.Unit.UnitPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitInterface;

public class Player extends Sprite implements UnitInterface{

    // Attribut
    // HP
    private int hp;
    private int maxHp;
    // Armor
    private int armor;
    private int maxArmor;
    // Damage
    private int cacDamage;
    private float coolDown;
    private int resistance;
    // Speed
    private float speed;
    private float jumpSpeed;
    private boolean jump = false;
    //Other
    private Body body;
    private Texture texture;

    private long lastAttackTime = 0;
    private boolean facingRight = true;
    private boolean lastFacingRight = true; // Par défaut, regarde à droite
    //Animation
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private TextureRegion currentFrame;
    private float stateTime; // Garde la trace du temps écoulé

    // Animation pour l'attaque
    private Animation<TextureRegion> attackLeftAnimation;
    private Animation<TextureRegion> attackRightAnimation;
    private boolean isAttacking = false; // Indique si l'animation d'attaque est active
    private float attackStateTime; // Temps écoulé pour l'animation d'attaque

    // Constructeur
    public Player() {
        //HP
        this.hp = 100;
        this.maxHp = 100;
        //Armor
        this.armor = 20;
        this.maxArmor = 100;
        this.resistance=2;
        //Damage
        this.cacDamage = 10;
        this.coolDown = 0.2f;
        //Speed
        this.speed = 100;
        this.jumpSpeed = 0.2f;

        texture = new Texture("character/Player.png");

        // Divise la texture en 6 parties (3 pour chaque direction)
        TextureRegion[][] tmpFrames = TextureRegion.split(texture, 25, 17);

        // Animation vers la gauche (ligne 0)
        walkLeftAnimation = new Animation<>(0.1f, tmpFrames[0]);
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Animation vers la droite (ligne 1)
        walkRightAnimation = new Animation<>(0.1f, tmpFrames[1]);
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Initialise l'état
        stateTime = 0f;
        currentFrame = tmpFrames[1][0]; // Frame par défaut

        Texture attackTexture = new Texture("character/PlayerAttack.png");
        TextureRegion[][] attackFrames = TextureRegion.split(attackTexture, 25, 17);

        // Animation d'attaque vers la gauche
        attackLeftAnimation = new Animation<>(0.1f, attackFrames[0]);
        attackLeftAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Ne pas boucler

        // Animation d'attaque vers la droite
        attackRightAnimation = new Animation<>(0.1f, attackFrames[1]);
        attackRightAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Ne pas boucler


        setBounds(0, 0, 75/Main.PPM, 48/Main.PPM);
        setRegion(texture);
    }

    // Method
    public void createBody(World world,float x, float y){
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x/Main.PPM, y/Main.PPM);

        // Create our body in the world using our body definition
        this.body = world.createBody(bodyDef);

        this.body.setUserData("Player");

        CircleShape shape = new CircleShape();
        // Taille HIT-BOX
        shape.setRadius(20/Main.PPM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.35f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();
    }

    public void movement() {
        float textureOffsetX;
        float textureOffsetY = getHeight() / 2; // Moitié de la hauteur de la texture

        // Ajuste l'offset horizontal en fonction de la direction
        if (facingRight) {
            textureOffsetX = getWidth() / 4; // Décale légèrement vers la gauche
        } else {
            textureOffsetX = 3 * getWidth() / 4; // Décale légèrement vers la droite
        }

        setPosition(body.getPosition().x - textureOffsetX, body.getPosition().y - textureOffsetY);

        updateAnimation();

        // Jump
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.jump) {
            body.applyLinearImpulse(new Vector2(0, jumpSpeed), body.getWorldCenter(), true);
            this.jump = true; // Empêche le saut multiple
        }

        if (body.getLinearVelocity().y == 0) {
            this.jump = false;
        }
    }

    public void updateAnimation() {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;

        // Déclenchement de l'animation d'attaque
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isAttacking && canAttack()) {
            isAttacking = true; // Active l'animation d'attaque
            attackStateTime = 0; // Réinitialise le temps de l'animation
        }

        // Gestion des animations
        if (isAttacking) {
            // Animation d'attaque
            attackStateTime += delta;

            if (!facingRight) {
                currentFrame = attackLeftAnimation.getKeyFrame(attackStateTime);
            } else {
                currentFrame = attackRightAnimation.getKeyFrame(attackStateTime);
            }

            // Vérifie si l'animation est terminée
            if (attackLeftAnimation.isAnimationFinished(attackStateTime) ||
                    attackRightAnimation.isAnimationFinished(attackStateTime)) {
                isAttacking = false; // Réinitialise l'état
            }
        } else {
            // Animation de mouvement
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(-this.speed * delta, body.getLinearVelocity().y);
                currentFrame = walkLeftAnimation.getKeyFrame(stateTime);
                facingRight = false;
                lastFacingRight = false; // Met à jour la dernière direction
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(this.speed * delta, body.getLinearVelocity().y);
                currentFrame = walkRightAnimation.getKeyFrame(stateTime);
                facingRight = true;
                lastFacingRight = true; // Met à jour la dernière direction
            } else {
                // Joueur immobile
                body.setLinearVelocity(0, body.getLinearVelocity().y);

                // Conserve l'animation dans la dernière direction
                if (lastFacingRight) {
                    currentFrame = walkRightAnimation.getKeyFrame(0); // Frame idle à droite
                } else {
                    currentFrame = walkLeftAnimation.getKeyFrame(0); // Frame idle à gauche
                }
            }
        }
        // Met à jour la région de l'image
        setRegion(currentFrame);
    }

    public boolean canAttack() {
        long currentTime = TimeUtils.millis();
        if ((currentTime - lastAttackTime) >= (this.coolDown * 1000)) {
            lastAttackTime = currentTime; // Réinitialise le dernier temps d'attaque
            return true; // Cooldown écoulé, prêt pour attaquer
        }
        return false;
    }

    public void increaseHp(int amount){
        this.hp += amount;
        if (this.hp > this.maxHp){
            this.hp = this.maxHp;
        }
    }

    public void increaseMaxHp(int amount){
        this.maxHp += amount;
    }

    public void increaseSpeed(float amount){
        this.speed += amount;
    }

    public void increaseResistance(float amount){
        this.resistance += amount;
    }

    public void increaseArmor(int amount){
        this.armor += amount;
        if (this.armor > this.maxArmor){
            this.armor = this.maxArmor;
        }
    }

    public void increaseMaxArmor(int amount){
        this.maxArmor += amount ;
    }

    public void increaseDPS(int amount){
        this.cacDamage += amount;
    }

    public void decreaseCD(float amount) {
        this.coolDown -= amount;
        if (this.coolDown <= 0) {
            this.coolDown = 0.1f;
        }
    }

    public void decreaseHp ( int amount){
        if (armor > 0) {
            this.armor -= amount - resistance;
            if (armor <= 0) {
                armor = 0;
            }
        }
        if (armor == 0) {
            this.hp -= amount - resistance;
        }
        if (this.hp <= 0) {
            this.hp = 0;
        }
    }

    public void deathReset(){
        setHp(100);
        setMaxHp(100);
        setArmor(0);
        setMaxArmor(0);
        setCacDamage(10);
        setCoolDown(0.2f);
        setSpeed(100);

    }

    // Getter
    //HP
    public int getHp(){
        return this.hp;
    }

    public int getMaxHp(){
        return this.maxHp;
    }

    // Armor
    public int getArmor(){
        return this.armor;
    }

    public int getMaxArmor(){
        return this.maxArmor;
    }

    public int getResistance(){
        return this.resistance;
    }

    // Damage
    public int getCacDamage(){
        return this.cacDamage;
    }

    public float getCoolDown(){ return this.coolDown; }

    public boolean getIsAttacking(){ return this.isAttacking; }

    //Speed
    public float getSpeed(){
        return this.speed;
    }

    public float getJumpSpeed(){
        return this.jumpSpeed;
    }

    public boolean isJump(){
        return this.jump;
    }

    //Other
    public Texture getTexture(){
        return this.texture;
    }

    public Body getBody(){
        return this.body;
    }

    public boolean isFacingRight() {return facingRight;}

    public boolean isLastFacingRight() {return lastFacingRight;}

    public Vector2 getPosition(){
        return body.getPosition();
    }

    //SETTER

    //HP
    public void setHp(int hp){
        this.hp = hp;
    }

    public void setMaxHp(int maxHp){
        this.maxHp = maxHp;
    }

    //Armor
    public void setArmor(int armor){
        this.armor = armor;
    }

    public void setMaxArmor(int maxArmor){
        this.maxArmor = maxArmor;
    }

    public void setResistance(int resistance){
        this.resistance = resistance;
    }

    //Damage
    public void setCacDamage(int CacDamage){
        this.cacDamage = CacDamage;
    }

    public void setCoolDown(float coolDown){ this.coolDown = coolDown; }

    // Speed
    public void setSpeed(float speed){
        this.speed = speed;
    }

    public void setJumpSpeed(float jumpSpeed){
        this.jumpSpeed = jumpSpeed;
    }

    public void setFacingRight(boolean facingRight){this.facingRight = facingRight;}

    public void setLastFacingRight(boolean lastFacingRight) {this.lastFacingRight = lastFacingRight;}
}
