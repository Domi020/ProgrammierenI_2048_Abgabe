package game.model.leaderboard.test;

import game.model.DebugAI;
import game.model.Matrix;
import game.model.leaderboard.SaveGameManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveGameManagerTest {

    @Test
    public void createSaveData() {
        int[][] matrixField = {
                {0, 0, 2, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0}};
        Matrix matrix = new Matrix(matrixField);

        SaveGameManager saveGameManager = new SaveGameManager(matrix, new DebugAI(matrix));

        saveGameManager.saveGame(4);

        String filePath = new File("").getAbsolutePath() + "/savegame.tmp";
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createCorrectSaveData() {
        int[][] matrixField = {
                {0, 0, 2, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0}};
        Matrix matrix = new Matrix(matrixField);

        SaveGameManager saveGameManager = new SaveGameManager(matrix, new DebugAI(matrix));

        saveGameManager.saveGame(4);

        saveGameManager.loadGame();
        int[] assumed = {0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0};
        assertTrue(Arrays.equals(assumed, saveGameManager.getBoard()));
    }
}
