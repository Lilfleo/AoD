package com.nsm_X;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;

public class RenderManager {
    // private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    public RenderManager() {
        // shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
    }

    public void render(UnitManager entityManager) {
        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Rendu du sol
        // shapeRenderer.setColor(Color.BLUE);
        // shapeRenderer.rect(worldManager.getGround().getPosition().x, worldManager.getGround().getPosition().y, 600, 10);

        // shapeRenderer.end();

        spriteBatch.begin();

        // Rendu du joueur
        Player player = entityManager.getPlayer();
        if (player.getTexture() != null) {

            float playerWidth = 20;
            float playerHeight = 20;

            spriteBatch.draw(
                player.getTexture(),
                player.getBody().getPosition().x - playerWidth / 2,
                player.getBody().getPosition().y - playerHeight / 2,
                playerWidth,
                playerHeight
            );
        }

        // Rendu de l'ennemi
        Enemy enemy = entityManager.getEnemy();
        if (enemy.getTexture() != null) {

            float playerWidth = 20;
            float playerHeight = 20;

            spriteBatch.draw(
                enemy.getTexture(),
                enemy.getBody().getPosition().x - playerWidth / 2,
                enemy.getBody().getPosition().y - playerHeight / 2,
                playerWidth,
                playerHeight
            );
        }

        spriteBatch.end();
    }

    public void dispose() {
        // shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
