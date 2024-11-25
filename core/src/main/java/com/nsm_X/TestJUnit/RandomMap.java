// package com.nsm_X.TestJUnit;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.nsm_X.Tools.MapManager;

// class RandomMap {
	
// 	private MapManager mapManager;

	

// 	@BeforeEach
// 	void setUp() throws Exception {
// 		mapManager = new MapManager();
// 	}

// 	@AfterEach
// 	void tearDown() throws Exception {
// 	}

// 	 @Test
// 	    void testRandomMapReturnsDifferentMap() {
// 	        // Récupérer la carte actuelle
// 	        String initialMap = mapManager.getCurentMap();

// 	        // Appeler la méthode randomMap
// 	        String newMap = mapManager.randomMap();

// 	        // Vérifier que la nouvelle carte est différente de la carte actuelle
// 	        assertNotEquals(initialMap, newMap, "La nouvelle carte doit être différente de la carte actuelle.");
// 	    }
	 
// 	 @Test
// 	    void testRandomMapIsValid() {
// 	        // Récupérer la table des cartes disponibles
// 	        String[] validMaps = {
// 	            "map/Map01.tmx",
// 	            "map/Map02.tmx",
// 	            "map/Map03.tmx",
// 	            "map/Map04.tmx",
// 	            "map/Map05.tmx"
// 	        };

// 	        // Appeler la méthode randomMap
// 	        String newMap = mapManager.randomMap();

// 	        // Vérifier que la nouvelle carte appartient à la liste des cartes valides
// 	        assertTrue(
// 	            java.util.Arrays.asList(validMaps).contains(newMap),
// 	            "La carte retournée doit être dans la liste des cartes valides."
// 	        );
// 	    }

// 	    @Test
// 	    void testRandomMapUpdatesCurrentMap() {
// 	        // Appeler la méthode randomMap
// 	        String newMap = mapManager.randomMap();

// 	        // Vérifier que la carte actuelle a été mise à jour
// 	        assertEquals(newMap, mapManager.getCurentMap(), "La carte actuelle doit être mise à jour après l'appel de randomMap.");
// 	    }

// }
