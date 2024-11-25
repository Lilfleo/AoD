package com.nsm_X.Tools;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreManager {
    private static final String FILE_PATH = "highscores.json";

    // Sauvegarder un nouveau score et mettre à jour le Top 10
    public static void saveHighscore(int floorCounter) {
        Json json = new Json();
        FileHandle file = new FileHandle(FILE_PATH);

        ArrayList<Integer> topScores = file.exists() ? json.fromJson(ArrayList.class, Integer.class, file) : new ArrayList<>();

        // Ajouter le nouveau score
        topScores.add(floorCounter);

        // Trier les scores par ordre décroissant
        Collections.sort(topScores, Collections.reverseOrder());

        // Garder seulement les 10 meilleurs scores
        if (topScores.size() > 10) {
            topScores = new ArrayList<>(topScores.subList(0, 10));
        }

        // Sauvegarder les Top 10 dans le fichier JSON
        file.writeString(json.toJson(topScores), false);
    }

    // Récupérer le Top 10 des scores
    public static ArrayList<Integer> getTopScores() {
        FileHandle file = new FileHandle(FILE_PATH);
        Json json = new Json();

        if (file.exists()) {
            return json.fromJson(ArrayList.class, Integer.class, file);
        }

        return new ArrayList<>(); // Si le fichier n'existe pas, retourner une liste vide
    }
}
