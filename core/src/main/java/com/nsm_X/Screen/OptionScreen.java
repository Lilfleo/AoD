package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nsm_X.Main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class OptionScreen implements Screen {
    private Stage stage;
    private Main main;
    private Skin skin;

    private Table optionsMenuTable;

    // Audio components
    private Music music;
    private Sound soundEffect;
    private Slider musicSlider;
    private Slider soundSlider;

    public OptionScreen(Main main) {
        this.main = main;
        music = Gdx.audio.newMusic(Gdx.files.internal("music/OptionMenu.wav"));
        soundEffect = Gdx.audio.newSound(Gdx.files.internal("music/jump.wav"));

        // Initialisation des volumes (par défaut)
        music.setVolume(0.5f);  // Volume musique par défaut
        soundEffect.setVolume(0, 0.5f);  // Volume des effets sonores par défaut

        music.setVolume(0.1f);
        music.play();
        music.setLooping(true);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/SSui.json")); // Assure-toi d'avoir le fichier "uiskin.json"

        // Ajouter l'image de fond
        Texture backgroundTexture = new Texture(Gdx.files.internal("image/fond.png"));
        Image background = new Image(backgroundTexture);
        background.setFillParent(true); // L'image remplira toute la fenêtre
        stage.addActor(background);  // Ajouter l'image de fond à la scène

        // Sliders pour la musique et les effets sonores
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider.setValue(music.getVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.setVolume(musicSlider.getValue());
            }
        });

        soundSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundSlider.setValue(0.5f);  // Valeur par défaut
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundEffect.setVolume(0, soundSlider.getValue());
            }
        });

        // Ajouter un bouton pour revenir
        TextButton backButton = new TextButton("RETURN", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new StartScreen(main));  // Passer à l'écran principal
            }
        });

        // Ajout de l'option pour le mode plein écran / Fenêtré
        TextButton fullscreenButton = new TextButton("FULL SCREEN / WINDOW", skin);
        fullscreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isFullscreen = Gdx.graphics.isFullscreen();
                if (isFullscreen) {
                    Gdx.graphics.setWindowedMode(800, 600);  // Exemple de résolution en mode fenêtre
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());  // Passer en plein écran
                }
            }
        });

        // Option pour réinitialiser les paramètres
        TextButton resetButton = new TextButton("RESET SETTINGS", skin);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicSlider.setValue(0.5f);  // Réinitialiser la musique à 50%
                soundSlider.setValue(0.5f);  // Réinitialiser les effets sonores à 50%
                music.setVolume(0.5f);  // Réinitialiser le volume de la musique
                soundEffect.setVolume(0, 0.5f);  // Réinitialiser les effets sonores
            }
        });

        // Ajouter un SelectBox pour la résolution
        SelectBox<String> resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems("800x600", "1280x720", "1920x1080");
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedResolution = resolutionSelectBox.getSelected();
                switch (selectedResolution) {
                    case "800x600":
                        Gdx.graphics.setWindowedMode(800, 600);
                        break;
                    case "1280x720":
                        Gdx.graphics.setWindowedMode(1280, 720);
                        break;
                    case "1920x1080":
                        Gdx.graphics.setWindowedMode(1920, 1080);
                        break;
                }
            }
        });

        // Créer une table pour le menu d'options
        optionsMenuTable = new Table();
        optionsMenuTable.center();
        optionsMenuTable.setFillParent(true);

        optionsMenuTable.add(new Label("MUSIC", skin)).padBottom(10).row();
        optionsMenuTable.add(musicSlider).padBottom(20).row();
        optionsMenuTable.add(new Label("SOUND EFFECTS", skin)).padBottom(10).row();
        optionsMenuTable.add(soundSlider).padBottom(20).row();
        optionsMenuTable.add(new Label("RESOLUTION", skin)).padBottom(10).row();
        optionsMenuTable.add(resolutionSelectBox).padBottom(20).row();
        optionsMenuTable.add(fullscreenButton).padBottom(20).row();
        optionsMenuTable.add(resetButton).padBottom(20).row();
        optionsMenuTable.add(backButton).padBottom(20).row();

        stage.addActor(optionsMenuTable);
    }

    @Override
    public void render(float delta) {
        // Effacer l'écran
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner les éléments de l'écran
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));  // Mettre à jour les éléments de l'UI
        stage.draw();  // Dessiner le stage
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
        music.stop();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
