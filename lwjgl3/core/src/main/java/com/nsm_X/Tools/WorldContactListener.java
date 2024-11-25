package com.nsm_X.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nsm_X.Unit.UnitPC.Player;

public class WorldContactListener  implements ContactListener {
	
	private WorldCreator parent;
	private Player player;
	
	public WorldContactListener(WorldCreator parent) {
		this.parent = parent;
	}

	@Override
	public void beginContact(Contact contact) {
		
		
		
		Fixture fa = contact.getFixtureA();
	    Fixture fb = contact.getFixtureB();
	    
	    if (fa == null || fb == null) return;
	    if (fa.getUserData()== null ||fa.getUserData()== null)return;
	    
	    System.out.println("coniact");
	    
	
	    
		
		
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
