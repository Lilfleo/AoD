package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.nsm_X.Main;
import com.nsm_X.Tools.HighscoreManager;

import java.util.ArrayList;

public class FallOverScreen implements Screen {

    private Main main;
    private Skin skin;
    private Stage stage;  // Crée un Stage
    private Table table;
    private Music music;
    private boolean isVisible;


    public FallOverScreen(Main main) {
        this.main = main;
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));
        table = new Table();
        stage = new Stage();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Ending.wav"));
        music.play();
        music.setLooping(true);// Initialiser le Stage
    }

    @Override
    public void show() {
        Label titleLabel = new Label("YOU FELL DOWN", skin);
        titleLabel.setFontScale(4);
        titleLabel.setColor(Color.RED);

        Label undertitleLabel = new Label("at stage : " +main.getFloorCounter()+".", skin);

        ArrayList<Integer> topScores = HighscoreManager.getTopScores();
        int bestScore = topScores.isEmpty() ? 0 : topScores.get(0);// Si pas de scores, meilleur = 0
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.GOLD);

        Label bestScoreLabel;
        if (main.getFloorCounter() > bestScore) {
            bestScoreLabel = new Label("New Highscore! Best: " + main.getFloorCounter(), skin);}
        else {
            bestScoreLabel = new Label("High Score: " + bestScore, skin);
        }
        TextButton startButton = new TextButton("PLAY AGAIN ?", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setFloorCounter(0);
                main.setScreen(new StartScreen(main));  // Passer à l'écran de jeu
            }
        });

        Label coin = new Label("     INSERT 1 CREDIT  !    ", skin);
        coin.setColor(Color.GOLD);

        isVisible = true;

        // Utilise un Timer pour alterner la visibilité
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isVisible = !isVisible;
                coin.setVisible(isVisible);
            }
        }, 0, 0.5f); // 0.5f est l'intervalle de clignotement en secondes

        // Disposer les boutons dans un tableau
        table.add(titleLabel).center().padBottom(30).row();
        table.add(undertitleLabel).center().padBottom(30).row();
        table.add(bestScoreLabel).center().padBottom(30).row();
        table.center();
        table.setFillParent(true);
        table.add(startButton).padBottom(20).width(300).height(80).row();
        table.add(coin).center().padBottom(20).width(300).height(80);
        table.row();


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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();// N'oublie pas de libérer les ressources du stage
        music.dispose();
    }
}
