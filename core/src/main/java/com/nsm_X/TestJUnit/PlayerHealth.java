// package com.nsm_X.TestJUnit;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import com.badlogic.gdx.graphics.Texture;
// import com.nsm_X.Main;
// import com.nsm_X.Unit.UnitPC.Player;

// class PlayerHealth {
// 	 private Texture texture;

// 	private Player player;

//     @BeforeEach
//    public  void setUp() {
    	
    	
    	
//       player = new Player();
     
 
        
  
//     }

//     @Test
//     void testIncreaseHp() {
//         // Cas 1 : Augmenter les PV sans dépasser le maximum
//         player.setHp(50); // Définit les PV actuels
//         player.increaseHp(20); // Ajoute 20 PV
//         assertEquals(70, player.getHp(), "Les PV doivent être égaux à 70 après l'augmentation.");

//         // Cas 2 : Augmenter les PV en dépassant le maximum
//         player.setHp(90); // Définit les PV actuels
//         player.increaseHp(20); // Ajoute 20 PV
//         assertEquals(100, player.getHp(), "Les PV ne doivent pas dépasser le maximum de 100.");
//     }

//     @Test
//     void testDecreaseHp() {
//         // Cas 1 : Réduire les PV sans armure
//         player.setHp(50);
//         player.setArmor(0); // Pas d'armure
//         player.decreaseHp(20); // Enlève 20 PV
//         assertEquals(32, player.getHp(), "Les PV doivent être réduits à 32 après la réduction (avec 2 de résistance).");

//         // Cas 2 : Réduire les PV avec armure restante
//         player.setHp(50);
//         player.setArmor(10); // Armure active
//         player.decreaseHp(15); // Enlève 15 PV
//         assertEquals(37, player.getHp(), "Les PV ne doivent pas changer si l'armure absorbe les dégâts.");
//         assertEquals(0, player.getArmor(), "L'armure doit être réduite à 3 après la réduction.");

//         // Cas 3 : Réduire les PV avec armure insuffisante
//         player.setHp(50);
//         player.setArmor(5); // Faible armure
//         player.decreaseHp(15); // Enlève 15 PV
//         assertEquals(37, player.getHp(), "Les PV doivent être réduits à 42 après que l'armure est épuisée.");
//         assertEquals(0, player.getArmor(), "L'armure doit être épuisée (0).");

//         // Cas 4 : Les PV ne doivent pas descendre en dessous de zéro
//         player.setHp(5);
//         player.setArmor(0);
//         player.decreaseHp(10); // Enlève plus de PV que le joueur n'en possède
//         assertEquals(0, player.getHp(), "Les PV ne doivent pas être négatifs.");
//     }
// }
