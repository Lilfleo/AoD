# ASCENSION OF DOWN : A KNIGHT AGAINST ETHERNITY

**Step into a world where faith meets technology, and only the bravest can escape the grip of the Eternal Tower.**

![ASCENSION OF DOWN ](assets/image/miniature_guithub.png)

## Summary

In the distant future, the galaxy is torn apart by endless wars, but ancient beliefs and traditions survive in some remote corners.  
You play the role of a knight, part of an ancestral order that upholds the ideals of honor, faith and bravery. During a sacred mission, an unknown force draws you into the Eternal Tower, a colossal structure rising beyond the heavens.  
This tower, forged by a forgotten civilization, is a labyrinth of steel and stone where forbidden technologies, mechanical creatures and evil forces gravitate.  
Your plate armor, forged weapons and unshakeable faith are your only assets in this unforgiving place.    
Despite your best efforts to reach the exit, you discover that the tower seems infinite, and its changing architecture defies reality itself. Fragments of history and ancient whispers suggest that this tower is a purgatory, a prison designed to test brave souls.  
Your quest is no longer just a matter of survival: it’s a test of your faith and your willingness to accept the impossible. Will you be the one to break the tower’s curse, or will you succumb, like so many others before you?”


## How to play the game

The gameplay is simple yet challenging:

- **Move**: Use Left and Right arrows to navigate inside the tower.
- **Attack**: Press Up arrow to strike enemies with your weapon.
- **Jump**: Spacebar to overcome obstacles or dodge attacks.

**Your goal**:

- Survive as long as possible.
- Solve the mystery of the Eternal Tower.
- Upgrade your character to face increasingly tough challenges.

## Technologies Used

This project was built using the following technologies and tools:
- **Programming Language**: Java
- **Game Engine**: [LibGDX](https://libgdx.com/)
- **Graphics**:  Photoshop, Tiled
- **Version Control**: Git, GitHub
- **IDE**: IntelliJ IDEA, Eclipse ,Visual Studio Code


## Requirements

- Java JDK 22
- An IDE (Integrated Development Environment) exemple : Eclipse, IntelliJ IDEA, Android Studio

## Importing and Running

* [Setup your development environment](https://libgdx.com/wiki/start/setup)

* Clone the repository:

* [Import the project into your preferred development environment, run it](https://libgdx.com/wiki/start/import-and-running)


## Tree structure of the project
```
├── Choice
│   ├── AltStat.java
│   ├── AttackStat.java
│   ├── DefStat.java
│   └── StatModifier.java
├── Combat
│   ├── FightingArea.java
│   └── ProjectileManager.java
├── Items
│   ├── ArmorPotion.java
│   ├── Consommable.java
│   ├── ItemFactory.java
│   └── LifePotion.java
├── Main.java
├── PlayerManager.java
├── Projectile
│   ├── CasterProjectile.java
│   └── Projectile.java
├── Screen
│   ├── ChoiceMenu.java
│   ├── CreditScreen.java
│   ├── FallOverScreen.java
│   ├── GameOverScreen.java
│   ├── HighscoreScreen.java
│   ├── MainPlayScreen.java
│   ├── Option.java
│   ├── OptionScreen.java
│   └── StartScreen.java
├── Tools
│   ├── ArmorBar.java
│   ├── HealthBar.java
│   ├── HighscoreManager.java
│   ├── MapManager.java
│   ├── NotificationManager.java
│   ├── WorldContactListener.java
│   └── WorldCreator.java
├── Unit
│   ├── EnemyFactory.java
│   ├── UnitInterface.java
│   ├── UnitNPC
│   │   ├── Enemy.java
│   │   ├── Movement
│   │   │   ├── MoveApproachPattern.java
│   │   │   ├── MoveAvoidPattern.java
│   │   │   └── MovementStrategy.java
│   │   ├── NpcCac.java
│   │   ├── NpcCaster.java
│   │   └── NpcTank.java
│   └── UnitPC
│       └── Player.java
└── UnitManager.java
```

## Documentation

- **Javadoc**: A complete overview of the codebase with detailed class-level documentation.  
  Find it [Here](core/build/docs/javadoc/allclasses-index.html)

- **Game Design Document**: Includes design choices, game mechanics, story details, and more.
  Find it [Here](GDD_AoD.pdf)

## Roadmap

Here's what's planned for future updates:
- [ ] The player will have the ability to hit at range.
- [ ] Additional levels and environments.
- [ ] New enemies type.
- [ ] More weapon and consumable.



## Contributors

This project is brought to life thanks to the hard work and dedication of the following people:

- **Max LORIS**: Responsible for interface and menu design.
- **Antoine RIBEYRE**: Responsible for creating non-player characters.
- **Elio BRATTI**: Responsible for level design.
- **Juhani JUNKALA**: Music and Sound Effects.







