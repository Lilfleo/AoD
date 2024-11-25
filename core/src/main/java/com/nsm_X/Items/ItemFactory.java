package com.nsm_X.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Main;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    private Main main;
    private List<Consommable> consommables = new ArrayList<Consommable>();

    public ItemFactory(Main main) { this.main = main; }

    public List<Consommable> createConsommable(World world, float centerX, float centerY) {

        int consoCount;
        float count = main.getFloorCounter()*0.1f;

        if (main.getFloorCounter() == 0){
            consoCount = 0;
        }else {
            consoCount = MathUtils.random(0, 1 + (int)count);
        }

        for (int i = 0; i < consoCount; i++) {
            int flip = MathUtils.random(0, 3);
            if (flip == 0){
                consommables.add(new ArmorPotion(world,centerX, centerY));
            }else{
                consommables.add(new LifePotion(world,centerX, centerY));
            }
        }
        return consommables;
    }

    public void createConsommableSpawnZone(TiledMap map, World world) {
        MapLayer consoLayer = map.getLayers().get("spawn_item");
        if (consoLayer == null) {
            throw new IllegalStateException("Aucune couche 'spawn_item' trouvée dans la carte.");
        }

        for (MapObject object : consoLayer.getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            float centerX = (rect.getX() + rect.getWidth() / 2) / Main.PPM;
            float centerY = (rect.getY() + rect.getHeight() / 2) / Main.PPM;

            createConsommable(world, centerX, centerY);
        }
    }

    public void update(){
        for (Consommable consommable : consommables) {
            consommable.update();
        }
    }

    public void render(SpriteBatch batch) {
        main.batch.begin();
        for (Consommable consommable : consommables) {
            consommable.draw(batch);
        }
        main.batch.end();
    }
}
