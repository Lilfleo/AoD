package com.nsm_X.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nsm_X.Main;
import com.nsm_X.UnitManager;

public class WorldCreator {
	private UnitManager unitManager;

public WorldCreator(World world, TiledMap map, Main main, UnitManager unitManager, WorldContactListener worldContactListener) {
    this.unitManager = unitManager;

	world.setContactListener(worldContactListener);

	BodyDef  bdef = new BodyDef();
	PolygonShape shape = new PolygonShape();
	FixtureDef  fdef = new FixtureDef();
	Body body;


	for(MapObject object : map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class))
	{
		Rectangle rect = ((RectangleMapObject) object).getRectangle();
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((rect.getX() + rect.getWidth() / 2)/ Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

		body = world.createBody(bdef);

		shape.setAsBox(rect.getWidth()/2/ Main.PPM, rect.getHeight()/2/ Main.PPM);
		fdef.shape = shape;
		body.createFixture(fdef);
	}

	for(MapObject object : map.getLayers().get("exit").getObjects().getByType(RectangleMapObject.class))
	{
		Rectangle rect = ((RectangleMapObject) object).getRectangle();
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((rect.getX() + rect.getWidth() / 2)/ Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

		body = world.createBody(bdef);

		shape.setAsBox(rect.getWidth()/2/ Main.PPM, rect.getHeight()/2/ Main.PPM);
		fdef.shape = shape;
		body.createFixture(fdef);

		body.setUserData("Exit");

	}
}

}
