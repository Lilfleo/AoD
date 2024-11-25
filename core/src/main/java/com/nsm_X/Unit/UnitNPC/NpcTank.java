package com.nsm_X.Unit.UnitNPC;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class NpcTank extends Enemy{

    public NpcTank(World world, MovementStrategy movementStrategy, float x, float y){
        super(world, x, y);

        this.hp = 300;
        this.maxHp = 300;
        this.speed = 30;
        this.jumpSpeed = 200;

        this.mobDamage = 8;
        this.mobGcd = 4;
        this.mobRange = 2;
        this.mobFightRange = 0.5f;
        this.movementStrategy = movementStrategy;

        texture = new Texture("character/Tank.png");

        // Divise la texture en frames de 25x17 pixels
        TextureRegion[][] tmpFrames = TextureRegion.split(texture, 21, 16);

        // Animation vers la gauche (ligne 0)
        walkLeftAnimation = new Animation<>(0.5f, tmpFrames[0]);
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Animation vers la droite (ligne 1)
        walkRightAnimation = new Animation<>(0.5f, tmpFrames[1]);
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Définit la frame initiale
        currentFrame = tmpFrames[1][0];

        Texture attackTexture = new Texture("character/TankAttack.png");
        TextureRegion[][] attackFrames = TextureRegion.split(attackTexture, 21, 16);

        // Animation d'attaque vers la gauche
        attackLeftAnimation = new Animation<>(0.1f, attackFrames[0]);
        attackLeftAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Ne pas boucler

        // Animation d'attaque vers la droite
        attackRightAnimation = new Animation<>(0.1f, attackFrames[1]);
        attackRightAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Ne pas boucler

        setBounds(0, 0, 75 / Main.PPM, 48 / Main.PPM);
        setRegion(currentFrame);
    }
}
