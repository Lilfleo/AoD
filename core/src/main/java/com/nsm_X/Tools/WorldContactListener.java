package com.nsm_X.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nsm_X.Items.Consommable;
import com.nsm_X.Items.LifePotion;
import com.nsm_X.Main;
import com.nsm_X.Screen.ChoiceMenu;
import com.nsm_X.Screen.MainPlayScreen;
import com.nsm_X.Screen.StartScreen;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.UnitManager;

import java.util.ArrayList;
import java.util.List;

public class WorldContactListener implements ContactListener {

    private Main main;
    private UnitManager unitManager;
    private int countStage;
    private boolean isTransitioning = false; // Évite les transitions multiples
    private Player player;
    private MainPlayScreen mainPlayScreen;

    public WorldContactListener(Main main, MainPlayScreen mainPlayScreen) {
        this.main = main;
        this.mainPlayScreen = mainPlayScreen;
        this.countStage = 0;
        player = main.getPlayerManager().getPlayer();

    }
    public int getCount(){
        countStage++;
        return this.countStage;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Si le joueur atteint la sortie
        if (("Exit".equals(userDataA) && "Player".equals(userDataB))) {
            if (!isTransitioning) {
                isTransitioning = true; // Empêche les multiples transitions
                getCount();
                main.setScreen(new ChoiceMenu(main));
            }
        }

        // Si le joueur ramasse un consommable
        if ((userDataA instanceof Consommable && "Player".equals(userDataB)) ||
            (userDataB instanceof Consommable && "Player".equals(userDataA))) {

            Consommable consommable = userDataA instanceof Consommable ? (Consommable) userDataA : (Consommable) userDataB;

            consommable.applyEffect(player);
            consommable.setTexture(new Texture("image/empty.png"));

            // Ajoute le corps du consommable à la liste à détruire
            mainPlayScreen.markBodyForDestruction(consommable.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }

}
