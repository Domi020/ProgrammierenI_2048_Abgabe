package game.model.leaderboard;

import game.model.intf.ILeaderboardObservable;
import game.view.intf.ILeaderboardObserver;

import java.io.*;
import java.util.ArrayList;

/**
 * Leaderboards class which checks, if a score can get into the highscore list. Uses
 * readers and writers to save the leaderboards in a separate file.
 * Uses singleton, as only one instance of this class should exist at runtime.
 * Method init() must be called first to initialize the instance.
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 1.0.0
 */
public class Leaderboards implements ILeaderboardObservable {

    private static Leaderboards lBoard;
    private String filePath;
    private final String highScores;

    private final ArrayList<Integer> topScores;
    private final ArrayList<Integer> topTiles;

    private final ArrayList<ILeaderboardObserver> observers = new ArrayList<>();

    private Leaderboards() {
        try {
            filePath = new File("").getAbsolutePath();
            System.out.println(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        highScores = "Scores";

        topScores = new ArrayList<>();
        topTiles = new ArrayList<>();
    }

    public static Leaderboards getInstance() {
        if (lBoard == null) {
            lBoard = new Leaderboards();
        }
        return lBoard;
    }

    /**
     * Checks if the given score fits into the leaderboards. If it fits, the score is added.
     * @param score The score, which should be added
     */
    public void addScore(int score) {
        for (int i = 0; i < topTiles.size(); i++) {
            if (score >= topScores.get(i)) {
                topScores.add(i, score);
                topScores.remove(topScores.size() - 1);
                return;
            }
        }
    }

    /**
     * Checks if the given highest tile fits into the leaderboards. If it fits, the tile is added.
     * @param tileValue The tile, which should be added
     */
    public void addTile(int tileValue) {
        for (int i = 0; i < topTiles.size(); i++) {
            if (tileValue >= topTiles.get(i)) {
                topTiles.add(i, tileValue);
                topTiles.remove(topTiles.size() - 1);
                return;
            }
        }
    }

    /**
     * Initializes the instance by loading the separate file and preparing the lists
     */
    public void init() {
        try {
            File f = new File(filePath, highScores);
            if (!f.isFile()) {
                createSaveData();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            topScores.clear();
            topTiles.clear();

            String[] scores = reader.readLine().split("-");
            String[] tiles = reader.readLine().split("-");

            for (String score : scores) {
                topScores.add(Integer.parseInt(score));
            }
            for (String tile : tiles) {
                topTiles.add(Integer.parseInt(tile));
            }
            reader.close();
            notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
            if (resetFile()) init();
            else System.exit(-1);
        }
    }

    /**
     * Deletes the file, if it exists
     * @return bool
     */
    public boolean resetFile() {
        File f = new File(filePath, highScores);
        if (f.isFile()) {
            return f.delete();
        }
        return false;
    }

    public void saveScores() {
        FileWriter output;

        try {
            File f = new File(filePath, highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4));
            writer.newLine();
            writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createSaveData() {
        try {
            File file = new File(filePath, highScores);

            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write(Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore(){
        return topScores.get(0);
    }

    public ArrayList<Integer> getTopScores() {
        return topScores;
    }

    public ArrayList<Integer> getTopTiles() {
        return topTiles;
    }

    @Override
    public void registerObserver(ILeaderboardObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ILeaderboardObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void resetObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        for (ILeaderboardObserver observer : observers) {
            observer.refreshScore();
        }
    }

    @Override
    public ArrayList<Integer> getHighscores() {
        return getTopScores();
    }

    @Override
    public ArrayList<Integer> getTiles() {
        return getTopTiles();
    }
}

