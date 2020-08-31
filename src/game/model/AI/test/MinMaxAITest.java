package game.model.AI.test;

import game.model.AI.MinMaxAI;
import game.model.intf.IMatrix;
import game.model.test.RandomMatrixMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinMaxAITest {
    private MinMaxAI minMaxAI;
    private IMatrix game;
    private int[][] innerMatrix;

    @BeforeEach
    public void init() {
        innerMatrix = new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 0, 2, 4},
                {4, 2, 4, 2}};
        game = new RandomMatrixMock(innerMatrix);
        minMaxAI = new MinMaxAI(game);
    }

    @Test
    public void insertAnyTile() {
        assertTrue(minMaxAI.createTile());
    }

    @Test
    public void insertRightTileAtOnePosition() {
        minMaxAI.createTile();

        assertEquals(innerMatrix[2][1], 2);
    }

    @Test
    public void insertAtRightPosition() {
        for (int i = 0; i < 1000; i++) {
            //TODO: braucht spez. Mover-Mock
            game.insertTile(1, 3, 0);
            game.insertTile(1, 2, 8);
            game.insertTile(1, 1, 64);
            game.insertTile(2, 1, 0);
            game.insertTile(2, 2, 64);
            game.insertTile(2, 3, 4);

            minMaxAI.createTile();

            assertEquals(innerMatrix[2][1], 0);
            assertEquals(innerMatrix[1][3], 2);
        }
    }
}
