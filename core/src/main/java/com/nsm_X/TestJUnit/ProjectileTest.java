// package com.nsm_X.TestJUnit;

// import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.math.Vector2;
// import com.nsm_X.Projectile.Projectile;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

// class ProjectileTest {

//     private static class TestProjectile extends Projectile {
//         public TestProjectile(Vector2 position, Vector2 direction, float speed, int damage, boolean isEnemyProjectile) {
//             super(position, direction, speed, damage, isEnemyProjectile);
//         }
//     }

//     private TestProjectile projectile;

//     @BeforeEach
//     void setUp() {
//         Vector2 position = new Vector2(0, 0);
//         Vector2 direction = new Vector2(1, 0); // Direction vers la droite
//         float speed = 5.0f;
//         int damage = 10;
//         boolean isEnemyProjectile = true;

//         projectile = new TestProjectile(position, direction, speed, damage, isEnemyProjectile);
//     }

//     @Test
//     void testProjectileInitialization() {
//         assertEquals(new Vector2(0, 0), projectile.getPosition());
//         assertEquals(10, projectile.getDamage());
//         assertTrue(projectile.isEnemyProjectile());
//         assertTrue(projectile.isActive());
//     }

//     @Test
//     void testProjectileUpdate() {
//         projectile.update();
//         assertEquals(new Vector2(5, 0), projectile.getPosition()); // Avancé de 5 unités en x
//     }

//     @Test
//     void testDeactivateProjectile() {
//         projectile.deactivate();
//         assertFalse(projectile.isActive());
//         projectile.update(); // Même après la désactivation, la position ne devrait pas changer
//         assertEquals(new Vector2(0, 0), projectile.getPosition());
//     }

  
  
// }
