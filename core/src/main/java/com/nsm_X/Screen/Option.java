package com.nsm_X.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nsm_X.Main;

public class Option {

    private Stage stage;
    private Main main;
    Skin skin;
    private Table pauseMenuTable;
    private Table optionsMenuTable;

    // Audio components
    public Music music;
    private Sound soundEffect;
    private Slider musicSlider;
    private static Slider soundSlider;

    public Option(Main main) {
        this.main = main;

        // Initialisation de la scène UI
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Charger le skin pour l'UI
        skin = new Skin(Gdx.files.internal("skin/SSui.json"));

        // Charger les éléments audio (peut être modifié pour correspondre à ton jeu)
        music = Gdx.audio.newMusic(Gdx.files.internal("music/mainSound.wav"));
        soundEffect = Gdx.audio.newSound(Gdx.files.internal("music/jump.wav"));

        // Initialisation des volumes (par défaut)
        music.setVolume(0.5f);  // Volume musique par défaut
        soundEffect.setVolume(0, 0.5f);  // Volume des effets sonores par défaut

        // Créer un bouton "Pause"
        Texture gearTexture = new Texture(Gdx.files.internal("image/gearWhite.png"));
        float scale = 0.1f;  // Ajuste la taille de l'image et du bouton
        TextureRegion gearRegion = new TextureRegion(gearTexture);
        gearRegion.setRegion(0, 0, gearTexture.getWidth(), gearTexture.getHeight());
        TextureRegionDrawable gearDrawable = new TextureRegionDrawable(gearRegion);
        gearDrawable.setMinWidth(gearTexture.getWidth() * scale);
        gearDrawable.setMinHeight(gearTexture.getHeight() * scale);

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = gearDrawable;
        ImageButton pauseButton = new ImageButton(buttonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showExitMenu();  // Afficher le menu de pause
            }
        });

        // Table pour les boutons
        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.row().padTop(10).padLeft(10);
        table.add(pauseButton);
        stage.addActor(table);
        music.play();
        music.setLooping(true);
    }

    // Méthode pour afficher le menu de pause
    private void showExitMenu() {
        TextButton quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showExitConfirmation();  // Afficher la boîte de dialogue pour quitter
            }
        });

        TextButton resumeButton = new TextButton("RESUME", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenuTable.remove();  // Fermer le menu de pause
            }
        });

        TextButton optionButton = new TextButton("OPTION", skin);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showOptionsMenu();
            }
        });
        TextButton backButton = new TextButton("RETURN MAIN MENU", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new StartScreen(main));  // Passer à l'écran de jeu
            }
        });

        pauseMenuTable = new Table();
        pauseMenuTable.center();
        pauseMenuTable.setFillParent(true);
        pauseMenuTable.add(resumeButton).padBottom(20).width(330).height(70).row();
        pauseMenuTable.add(optionButton).padBottom(20).width(330).height(70).row();
        pauseMenuTable.add(backButton).padBottom(20).width(330).height(70).row();
        pauseMenuTable.add(quitButton).padBottom(20).width(330).height(70).row();
        stage.addActor(pauseMenuTable);
    }

    // Méthode pour afficher le menu des options avec sliders
    private void showOptionsMenu() {
        // Slider pour la musique
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider.setValue(music.getVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.setVolume(musicSlider.getValue());  // Appliquer le volume de la musique
            }
        });

        // Slider pour les effets sonores
        soundSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundSlider.setValue(0.5f);  // Valeur par défaut
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundEffect.setVolume(0, soundSlider.getValue());  // Appliquer le volume des effets sonores
            }
        });

        // Ajouter un bouton pour revenir
        TextButton backButton = new TextButton("RETURN", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsMenuTable.remove();  // Fermer le menu des options
            }
        });

        // Créer une table pour le menu d'options
        optionsMenuTable = new Table();
        optionsMenuTable.center();
        optionsMenuTable.setFillParent(true);

        optionsMenuTable.add(new Label("MUSIC", skin)).padBottom(10).row();
        optionsMenuTable.add(musicSlider).padBottom(20).row();  // Ajouter le slider pour la musique
        optionsMenuTable.add(new Label("SOUND EFFECTS", skin)).padBottom(10).row();
        optionsMenuTable.add(soundSlider).padBottom(20).row();  // Ajouter le slider pour les effets sonores
        optionsMenuTable.add(backButton).padBottom(20).row();  // Ajouter le bouton de retour

        stage.addActor(optionsMenuTable);

        // Retirer le menu de pause si visible
        if (pauseMenuTable != null) {
            pauseMenuTable.remove();
        }
    }
    public static float getSoundVolume() {
        return soundSlider != null ? soundSlider.getValue() : 0.5f; // Retourne le volume ou une valeur par défaut
    }

    // Méthode pour cacher le menu des options
    private void hideOptionsMenu() {
        if (optionsMenuTable != null) {
            pauseMenuTable.remove();  // Retirer la table contenant le menu des options
        }
        showExitMenu();  // Ré-afficher le menu de pause
    }
    private void hidePauseMenu() {
        if (optionsMenuTable != null) {
            optionsMenuTable.remove();  // Retirer la table contenant le menu des options
        }
        showExitMenu();  // Ré-afficher le menu de pause
    }
    // Méthode pour afficher la boîte de dialogue de confirmation pour quitter
    private void showExitConfirmation() {
        Dialog dialog = new Dialog("", skin){
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    Gdx.app.exit();  // Quitter l'application si l'utilisateur confirme
                }
            }
        };
        // Ajouter le texte du dialogue
        dialog.text("ARE YOU SURE YOU WANT TO LEAVE?");

        // Ajouter les boutons
        dialog.button("YES", true);
        dialog.button("NO", false);

        // Appliquer des pads aux tables de contenu et de boutons
        dialog.getContentTable().pad(20);  // Espacement autour du contenu (texte)
        dialog.getButtonTable().pad(10);   // Espacement autour des boutons

        // Personnaliser la taille du dialogue
        dialog.setWidth(400);  // Largeur
        dialog.setHeight(200); // Hauteur

        // Afficher le dialogue
        dialog.show(stage);

    }
    private SelectBox<String> resolutionSelectBox;


    private void showGraphicsOptions() {
        // Option de résolution
        resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems("800x600", "1024x768", "1920x1080");
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedResolution = resolutionSelectBox.getSelected();
                // Appliquer la résolution sélectionnée
                if (selectedResolution.equals("800x600")) {
                    Gdx.graphics.setWindowedMode(800, 600);
                } else if (selectedResolution.equals("1024x768")) {
                    Gdx.graphics.setWindowedMode(1024, 768);
                } else if (selectedResolution.equals("1920x1080")) {
                    Gdx.graphics.setWindowedMode(1920, 1080);
                }
            }
        });


        // Ajouter les options dans le menu
        optionsMenuTable.add(new Label("RESOLUTION", skin)).padBottom(10).row();
        optionsMenuTable.add(resolutionSelectBox).padBottom(20).row();

    }

    // Méthode pour mettre à jour la scène
    public void update(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Mise à jour de la scène
    }

    // Méthode pour dessiner l'UI
    public void draw() {
        stage.draw();
    }

    // Méthode pour gérer la taille de l'écran
    public void resize(int width, int height) {

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    // Méthode pour libérer les ressources
    public void dispose() {
        stage.dispose();
        music.stop();
        music.dispose();
        soundEffect.dispose();
    }

    // Accesseurs pour récupérer la scène
    public Stage getStage() {
        return stage;
    }
}
