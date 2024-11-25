package com.nsm_X.Tools;

import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nsm_X.Main;

public class MapManager {
	private Hashtable<String,String> mapTable;
	private TmxMapLoader maploader;
	private Vector2 playerStart;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private String mapName;
    private String curentMap = "Map00.tmx";
    
    private final static String lEVEL0 = "tutorial";
    private final static String lEVEL1 = "Map01";
    private final static String lEVEL2 = "Map02";
    private final static String lEVEL3 = "Map03";
    private final static String lEVEL4 = "Map04";
    private final static String lEVEL5 = "Map05";
    
    
    
    

	
	
	public MapManager() {
		mapTable = new Hashtable<String, String>();
			
	}
	public void curentMap(String mapName){
		
		 
	}
	
	public String randomMap(){
		mapTable.put("tutorial","Map00.tmx");
		mapTable.put("Map01","Map01.tmx");
		mapTable.put("Map02","Map02.tmx");
		mapTable.put("Map03","Map03.tmx");
		mapTable.put("Map04","Map04.tmx");
		mapTable.put("Map05","Map05.tmx");
		
		//quelle est la carte acuelle?
		System.out.println(curentMap);
		
		
		String[] maps = mapTable.keySet().toArray(new String[mapTable.size()]);
		
        String map = maps[new Random().nextInt(maps.length)];
        
        
        System.out.println( mapTable.get(map));
        
        
        
       if (mapTable.get(map) != curentMap )
       {
    	   curentMap = mapTable.get(map);
           System.out.println("la  nouvelle carte acuelle est "+curentMap);
       }
       
       if (mapTable.get(map) == "Map00.tmx" )
       {
    	   System.out.println("tutoriel ");
       }
    	 
    	   
       
        
       
		return mapTable.get(map);
		
		
	}
	public String setCurentMap() {
		return curentMap;
	}

}
