package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.nsm_X.Main;

public class CreditScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private  Main main;
    private Skin skin;
    private Music music;
    private Stage stage;

    public CreditScreen(Main main) {
        stage = new Stage();
        this.main = main;
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));
        batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/sucess.mp3"));
        music.play();
        music.setLooping(true);
    }

    @Override
    public void show() {
        BitmapFont font = new BitmapFont();
        skin.add("default-font.ftn", font);
        Texture backgroundTexture = new Texture(Gdx.files.internal("image/fond.png"));
        Image background = new Image(backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);
    }

    @Override
    public void render(float delta) {

        BitmapFont font = new BitmapFont();
        skin.add("default-font.ftn", font);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Fond noir

        stage.act(delta);
        stage.draw();

        batch.begin();

        // Titre des crédits
        font.setColor(1, 1, 0, 1);
        font.getData().setScale(3);
        font.draw(batch, "Crédits", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 50, 0, Align.center, false);

        // Liste des crédits
        font.getData().setScale(1.25f);
        font.draw(batch, "Ascension of the Dawn dev team: \n"+
                "Responsible for creating non-player characters: Antoine RIBEYRE \n\n" +
                "Responsible for interface and menu design: Max LORIS\n\n"+
                "Responsible for level design: Elio BRATTI \n\n"+
                "Music and Sound Effects: Juhani JUNKALA \n\n" +
                "\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n\nThank you for playing 'Ascension of the Dawn'!",
            Gdx.graphics.getWidth() / 2f,
            Gdx.graphics.getHeight() - 150,
            0,
            Align.center,
            false);

        // Option pour revenir au menu principal
        font.getData().setScale(1);
        font.draw(batch, "Press [ESC] to return to the main menu",
            Gdx.graphics.getWidth() / 2f,
            50,
            0,
            Align.center,
            false);

        batch.end();

        // Gestion des entrées
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            main.setScreen(new StartScreen(main)); // Remplace par ton écran de menu principal
        }
    }

    @Override
    public void resize(int width, int height) {
        // Gérer le redimensionnement si nécessaire
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        music.dispose();
    }
}
