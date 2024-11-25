package com.nsm_X.Unit.UnitNPC.Movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MoveAvoidPattern implements MovementStrategy{

    @Override
    public void move(Body mobBody, Vector2 playerPos, float speed) {

        float delta = Gdx.graphics.getDeltaTime();
        Vector2 mobPosition = mobBody.getPosition();

        // Inverser la direction de l'approche pour s'éloigner du joueur
        float direction = Math.signum(mobPosition.x - playerPos.x);  // Direction opposée à l'approche

        // Appliquer le mouvement pour s'éloigner du joueur
        mobBody.setLinearVelocity(direction * (speed * delta), mobBody.getLinearVelocity().y);
    }

}
