package com.nsm_X.Unit.UnitNPC.Movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MoveApproachPattern implements MovementStrategy{

    @Override
    public void move(Body mobBody, Vector2 playerPos, float speed){

        float delta = Gdx.graphics.getDeltaTime();
        Vector2 mobPosition = mobBody.getPosition();
        float direction = Math.signum(playerPos.x - mobPosition.x);

        mobBody.setLinearVelocity(direction*(speed*delta), mobBody.getLinearVelocity().y);
    }
}
