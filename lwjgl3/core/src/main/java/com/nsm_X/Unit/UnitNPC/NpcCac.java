package com.nsm_X.Unit.UnitNPC;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Unit.UnitNPC.Movement.MovementStrategy;

public class NpcCac extends Enemy{

    // Getter 

    public int getHp(){
        return this.hp;
    }

    public int getAp(){
        return this.ap;
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
    
    public Texture getTexture(){
        return this.texture;
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
    
    //Setter
    
    void setHp(int hp){
        this.hp = hp;
    }
    
    public void setAp(int ap){
        this.ap = ap;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public void setDamange(int damage){
        this.mobDamage = damage;
    }

    public void setJumpSpeed(float jumpSpeed){
        this.jumpSpeed = jumpSpeed;
    }

    public void setGcd(float gcd){
        this.mobGcd = gcd;
    }

    public void setFightRange(float fightRange){
        this.mobFightRange = fightRange;
    }

    public void setRange(float range){
        this.mobRange = range;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy){
        this.movementStrategy = movementStrategy;
    }
    
    public NpcCac(World world, MovementStrategy movementStrategy){
        super(world);

        this.hp = 130;
        this.ap = 200; 
        this.speed = 2000;
        this.jumpSpeed = 200;

        this.mobDamage = 4;
        this.mobGcd = 2;
        this.mobRange = 100;
        this.mobFightRange = 50;
        this.movementStrategy = movementStrategy;

        this.texture = new Texture("enemy01.png");
    }

    // Method


}