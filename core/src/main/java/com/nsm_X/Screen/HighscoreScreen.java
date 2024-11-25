package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nsm_X.Main;
import com.nsm_X.Tools.HighscoreManager;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;

public class HighscoreScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private ArrayList<Label> scoreLabels;
    private Main main;
    private Table table;
    private Music music;

    public HighscoreScreen(Main main) {
        stage = new Stage();
        table = new Table();
        this.main = main;
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/sucess.mp3"));
        music.play();
        music.setLooping(true);

        scoreLabels = new ArrayList<>();
    }

    @Override
    public void show() {
        // Charger l'image de fond
        Texture backgroundTexture = new Texture(Gdx.files.internal("image/fond.png"));
        Image background = new Image(backgroundTexture);
        background.setFillParent(true); // L'image remplira toute la fenêtre

        // Ajouter l'image de fond en premier
        stage.addActor(background);
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);
        skin.add("font-title-export.ftn", font);

        // Créer un label pour le titre
        Label titleLabel = new Label("HALL OF LEGENDS", new Label.LabelStyle(skin.getFont("title"), skin.getColor("bloodRed")));
        titleLabel.setFontScale(0.9f); // Taille plus grande pour le titre
        titleLabel.setAlignment(Align.center); // Centrer le texte
        titleLabel.setPosition(stage.getWidth() / 2 - titleLabel.getWidth() / 2, stage.getHeight() - titleLabel.getHeight() - 20); // Positionner le titre en haut

        // Ajouter le label du titre à la scène
        stage.addActor(titleLabel);

        // Récupérer le Top 10 des scores
        ArrayList<Integer> topScores = HighscoreManager.getTopScores();

        // Créez une table et remplissez-la avec les scores
        table.top().center().padTop(50); // Centrer et ajouter du padding en haut

        // Affichage des Top 3 avec un style distinct
        for (int i = 0; i < topScores.size(); i++) {
            Label scoreLabel;
            if (i == 0) {
                scoreLabel = new Label(" THE LEGEND : " + topScores.get(i), new Label.LabelStyle(font, skin.getColor("gold")));  // Couleur dorée pour le 1er
                scoreLabel.setFontScale(2f);  // Augmenter la taille du texte pour le 1er
            } else if (i == 1) {
                scoreLabel = new Label(" THE SECOND : " + topScores.get(i), new Label.LabelStyle(font, skin.getColor("silver")));  // Couleur argentée pour le 2e
                scoreLabel.setFontScale(1.8f);  // Taille un peu plus grande pour le 2e
            } else if (i == 2) {
                scoreLabel = new Label(" THE THIRD : " + topScores.get(i), new Label.LabelStyle(font, skin.getColor("bronze")));  // Couleur bronze pour le 3e
                scoreLabel.setFontScale(1.6f);  // Taille un peu plus grande pour le 3e
            } else {
                scoreLabel = new Label("TOP "+(i + 1) + " : " + topScores.get(i), new Label.LabelStyle(font, null));  // Style standard pour les autres
                scoreLabel.setFontScale(1.2f);  // Taille plus petite pour les autres
            }
            table.row().padBottom(10); // Ajouter un espacement entre les lignes
            table.add(scoreLabel).center(); // Centrer chaque score dans la table
        }

        // Créer le bouton Retour
        TextButton returnButton = new TextButton("RETURN", skin);
        returnButton.setSize(300, 60); // Taille du bouton
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new StartScreen(main));  // Passer à l'écran de jeu
            }
        });

        // Ajouter une nouvelle ligne pour le bouton et l'ajouter à la table
        table.row().padTop(20); // Espacement entre les scores et le bouton
        table.add(returnButton).center(); // Centrer le bouton dans la table

        // Ajoutez la table à l'écran
        table.setFillParent(true);  // Permet à la table de remplir toute la fenêtre
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage); // Permet d'interagir avec l'écran

        music.setVolume(1f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Mise à jour de la scène
        stage.draw(); // Dessine les éléments de la scène
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
        music.stop();
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
