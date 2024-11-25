package com.nsm_X.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Main;
import com.nsm_X.Unit.UnitPC.Player;

public class ArmorPotion extends Consommable{

    public ArmorPotion(World world, float x, float y) {
        super(world, x, y);
        this.amount = 10;

        texture = new Texture("image/ArmorPotion.png");
        setBounds(0, 0, 20.3f/ Main.PPM, 31.3f/Main.PPM);
        setRegion(texture);
    }

    @Override
    public void applyEffect(Player player) {
        player.increaseArmor(amount);
    }
}
