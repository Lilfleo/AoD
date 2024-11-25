package com.nsm_X;

import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Unit.UnitPC.Player;

public class PlayerManager {
    private Player player;
    private Main main;

    public PlayerManager(Main main)  {
        player = new Player();
    }

    public Player getPlayer() {
        return player;
    }

    public void update(){
        player.movement();
    }
//    public void render() {
//        main.batch.begin();
//        player.draw(main.batch);
//        main.batch.end();
//    }
}
