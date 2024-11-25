package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nsm_X.Main;

import com.badlogic.gdx.graphics.Texture;

public class StartScreen implements Screen {

    private Main main;
    private Skin skin;
    private Stage stage;
    private Table table;
    private Music music;

    public StartScreen(Main main) {
        this.main = main;
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));
        table = new Table();
        stage = new Stage();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/TitleScreen.wav"));
        music.setVolume(0.5f);
        music.play();
        music.setLooping(true);
    }

    @Override
    public void show() {
        // Charger l'image de fond
        Texture backgroundTexture = new Texture(Gdx.files.internal("image/fondStart.png"));
        Image background = new Image(backgroundTexture);
        background.setFillParent(true); // L'image remplira toute la fenêtre

        // Ajouter l'image de fond en premier
        stage.addActor(background);

        Label titleLabel = new Label("ASCENSION OF THE DAWN", new Label.LabelStyle(skin.getFont("title"), skin.getColor("bloodRed")));
        titleLabel.setFontScale(0.75f); // Taille plus grande pour le titre
        titleLabel.setAlignment(Align.center); // Centrer le texte
        titleLabel.setPosition(stage.getWidth() / 2 - titleLabel.getWidth() / 2, stage.getHeight() - titleLabel.getHeight() - 110); // Positionner le titre en haut

        Label underTitle = new Label("A knight against eternity", new Label.LabelStyle(skin.getFont("font"), skin.getColor("gold")));
        underTitle.setFontScale(0.9f); // Taille plus grande pour le titre
        underTitle.setAlignment(Align.center); // Centrer le texte
        underTitle.setPosition(stage.getWidth() / 2 - underTitle.getWidth() / 2, stage.getHeight() - underTitle.getHeight() - 180); // Positionner le titre en haut


        stage.addActor(titleLabel);
        stage.addActor(underTitle);

        // Création des boutons du menu
        TextButton startButton = new TextButton("NEW GAME", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setFloorCounter(0);
                main.setScreen(new MainPlayScreen(main));  // Passer à l'écran de jeu
            }
        });
        TextButton optionsButton = new TextButton("OPTIONS", skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new OptionScreen(main));  // Passer à l'écran de jeu
            }
        });
        TextButton scoreButton = new TextButton("HIGH SCORE", skin);
        scoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new HighscoreScreen(main));  // Passer à l'écran de jeu
            }
        });
        TextButton creditButton = new TextButton("CREDIT", skin);
        creditButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new CreditScreen(main));
            }
        });
        TextButton exitButton = new TextButton("LEAVE", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Quitter l'application
            }
        });

        // Disposer les boutons dans un tableau
        table.center();
        table.setFillParent(true);
        table.add(startButton).width(300).height(60).row();
        table.add(scoreButton).width(300).height(60).row();
        table.add(optionsButton).width(300).height(60).row();
        table.add(creditButton).width(300).height(60).row();
        table.add(exitButton).width(300).height(60).row();

        // Ajouter la table au Stage
        stage.addActor(table);  // Ajoute la table au stage

        // Définir le Stage comme InputProcessor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Effacer l'écran
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Couleur de fond noire

        // Rendu de la scène du menu
        main.batch.begin();
        stage.draw();  // Dessiner la scène (tout ce qui est ajouté à la stage)
        main.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);  // Mettre à jour le viewport
    }

    @Override
    public void hide() {
        stage.dispose();
        music.stop();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();  // N'oublie pas de libérer les ressources du stage
        music.dispose();
    }
}
