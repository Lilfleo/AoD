package com.nsm_X.Unit.UnitNPC.Movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface MovementStrategy {

    void move(Body mobBody, Vector2 playerPos, float speed);
}
