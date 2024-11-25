package com.nsm_X.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsm_X.Choice.AltStat;
import com.nsm_X.Choice.AttackStat;
import com.nsm_X.Choice.DefStat;
import com.nsm_X.Main;
import com.nsm_X.Tools.MapManager;
import com.nsm_X.Unit.UnitNPC.Enemy;
import com.nsm_X.Unit.UnitPC.Player;
import com.nsm_X.UnitManager;

public class ChoiceMenu implements Screen {
    private Stage stage;
    private Main main;
    private Skin skin;
    private Player player;
    private Music music;
    private MapManager mapManager;
    private Label tutoLabel;
    private Label titleLabel;
    private int floor;
    private TiledMap map;
    private DefStat defStat;
    private AttackStat attStat;
    private AltStat altStat;
    private String newMap;


    public ChoiceMenu(Main main) {
        this.main = main;
        player = main.getPlayerManager().getPlayer();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/EndLevel.mp3"));
        music.play();
        music.setLooping(true);
        this.floor = main.getFloorCounter();
        defStat=new DefStat(main);
        attStat=new AttackStat(main);
        altStat=new AltStat(main);

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mapManager = new MapManager();
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));

        if (floor == 0) {
            tutoLabel = new Label("CONGRATULATIONS", skin);
            tutoLabel.setFontScale(2);
        } else {
            titleLabel = new Label("YOU'VE FINISHED THE FLOOR :" + floor, skin);
            titleLabel.setFontScale(2);
        }

        // Initialisation des labels et des boutons
        Label stat1 = new Label("STRENGTH MODIFIER ", skin);
        stat1.setFontScale(1f);
        Label stat2 = new Label("DEFENSE MODIFIER", skin);
        stat2.setFontScale(1f);
        Label stat3 = new Label("GAMBLE CHOICE", skin);
        stat3.setFontScale(1f);
        Label descriptionLabel = new Label("CHOOSE AN OPTION BELOW TO ENHANCE YOUR CHARACTER", skin);

        TextButton button1 = new TextButton("STRENGTH", skin);
        TextButton button2 = new TextButton("DEFENSE", skin);
        TextButton button3 = new TextButton("FLIP IT ", skin);

        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                afficherStatistiques(1);
            }
        });

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                afficherStatistiques(2);
            }
        });

        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                afficherStatistiques(3);
            }
        });

        // Initialisation du DefStat avec un player de UnitManager


        Table table = new Table();
        table.center();
        table.top().padTop(100);
        table.setFillParent(true);

        table.add(titleLabel).padBottom(20).colspan(3);
        table.row();
        table.add(tutoLabel).padBottom(20).colspan(3);
        table.row();
        table.add(descriptionLabel).padBottom(40).colspan(3);
        table.row();
        table.add(stat1).padBottom(20).colspan(3);
        table.row();
        table.add(button1).padBottom(20).width(1080).height(90);
        table.row();
        table.add(stat2).padBottom(20).colspan(3);
        table.row();
        table.add(button2).padBottom(20).width(1080).height(90);
        table.row();
        table.add(stat3).padBottom(20).colspan(3);
        table.row();
        table.add(button3).width(1080).height(90);

        stage.addActor(table);
        music.setVolume(1f);
    }

    private void afficherStatistiques(int type) {


        switch (type) {
            case 1:
                attStat.randomChoice();  // Cela exécutera un changement aléatoir
                main.incrementFloorCounter();
                newMap = mapManager.randomMap();
                main.setScreen(new MainPlayScreen(this.main)); // Affichage d'une stat
                break;

            case 2:
                defStat.randomChoice();  // Cela exécutera un changement aléatoir
                main.incrementFloorCounter();
                newMap = mapManager.randomMap();
                main.setScreen(new MainPlayScreen(this.main)); // Affichage d'une stat
                break;

            case 3:
                altStat.randomChoice();  // Cela exécutera un changement aléatoir
                main.incrementFloorCounter();
                newMap = mapManager.randomMap();
                main.setScreen(new MainPlayScreen(this.main)); // Affichage d'une stat
                break;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        map.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
