package game.model.leaderboard;

import game.AIType;
import game.model.AI.AIFactory;
import game.model.DebugAI;
import game.model.intf.IMatrix;

import java.io.*;

/**
 * Provides methods to create a savegame or load a savegame
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 0.2.0
 */
public class SaveGameManager {

    private int currentScore;
    private int currentTopScore;
    private final int[] board = new int[16];

    private String filePath;
    private final String temp;
    private final IMatrix matrix;
    private final int matrixLength;

    private boolean newGame;

    private final DebugAI debugAI;

    public SaveGameManager(IMatrix matrix, DebugAI debugAI) {
        try {
            filePath = new File("").getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp = "savegame.tmp";

        this.matrix = matrix;
        matrixLength = matrix.returnMatrix().length;
        this.debugAI = debugAI;
    }

    /**
     * Resets a savegame, if exists.
     */
    public void reset() {
        File f = new File(filePath, temp);
        if (f.isFile()) {
            f.delete();
        }
        newGame = true;
        currentScore = 0;
    }

    private void createFile() {
        FileWriter output;
        newGame = true;

        try {
            File f = new File(filePath, temp);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();
            for (int row = 0; row < matrixLength; row++) {
                for (int col = 0; col < matrixLength; col++) {
                    if (row == matrixLength - 1 && col == matrixLength - 1) {
                        writer.write("" + 0);
                    } else {
                        writer.write(0 + "-");
                    }
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a current game into a separate file
     *
     * @param currentScore The current score in the game
     */
    public void saveGame(int currentScore) {
        FileWriter output;
        if (newGame) newGame = false;

        try {
            File f = new File(filePath, temp);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + currentScore);
            writer.newLine();
            writer.write("" + currentTopScore);
            writer.newLine();
            for (int row = 0; row < matrixLength; row++) {
                for (int col = 0; col < matrixLength; col++) {
                    this.board[row * matrixLength + col] = matrix.tileAt(row, col) != 0 ? matrix.tileAt(row, col) : 0;
                    if (row == matrixLength - 1 && col == matrixLength - 1)
                        writer.write("" + board[row * matrixLength + col]);
                    else writer.write(board[row * matrixLength + col] + "-");
                }
            }
            writer.newLine();
            if (AIFactory.getCurrentAIType() == AIType.RANDOM) {
                writer.write("random");
            } else if (AIFactory.getCurrentAIType() == AIType.COOPERATIVE) {
                writer.write("cooperative");
            } else if (AIFactory.getCurrentAIType() == AIType.MINMAX) {
                writer.write("minmax");
            } else if (AIFactory.getCurrentAIType() == AIType.MINMAXMULTI) {
                writer.write("minmaxMulti");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a game state from the file
     */
    public void loadGame() {
        try {
            File f = new File(filePath, temp);

            if (!f.isFile()) {
                createFile();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            currentScore = Integer.parseInt(reader.readLine());
            currentTopScore = Integer.parseInt(reader.readLine());

            String[] board = reader.readLine().split("-");
            int k = 0;
            for (int i = 0; i < matrixLength; i++) {
                for (int j = 0; j < matrixLength; j++) {
                    int value = Integer.parseInt(board[k]);
                    if (value > 0) {
                        debugAI.setCoordinates(i, j, value);
                        debugAI.createTile();
                    }
                    k++;
                }
            }
            String ai = reader.readLine();
            switch (ai) {
                case "random" -> AIFactory.setCurrentAI(AIType.RANDOM);
                case "cooperative" -> AIFactory.setCurrentAI(AIType.COOPERATIVE);
                case "minmax" -> AIFactory.setCurrentAI(AIType.MINMAX);
                case "minmaxMulti" -> AIFactory.setCurrentAI(AIType.MINMAXMULTI);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentTopScore() {
        return currentTopScore;
    }

    public void setCurrentTopScore(int currentTopScore) {
        this.currentTopScore = currentTopScore;
    }

    public int[] getBoard() {
        return board;
    }

}
