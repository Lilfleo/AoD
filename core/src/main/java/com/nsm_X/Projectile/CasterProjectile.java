package com.nsm_X.Projectile;

import com.badlogic.gdx.math.Vector2;

public class CasterProjectile extends Projectile {

    public CasterProjectile(Vector2 position, Vector2 direction) {
        super(position, direction, 0.02f, 5, true);
    }
}
