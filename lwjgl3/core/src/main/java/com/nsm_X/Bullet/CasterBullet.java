package com.nsm_X.Bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class CasterBullet extends Projectile{
    
    public CasterBullet(Vector2 startPosition, Vector2 direction){
        super(startPosition, direction, 300, 15, true);
        
        this.texture = new Texture("bullet_01.png");
    }

    
}
