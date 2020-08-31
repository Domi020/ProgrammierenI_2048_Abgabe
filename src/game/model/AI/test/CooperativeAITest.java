package game.model.AI.test;

import game.model.AI.CooperativeAI;
import game.model.intf.IMatrix;
import game.model.test.CoopMatrixMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CooperativeAITest {
    private CooperativeAI cooperativeAI;
    private int[][] testMatrix;

    @BeforeEach
    public void init() {
        testMatrix = new int[][]{
                {2, 2, 2, 2},
                {8, 2, 8, 4},
                {8, 0, 0, 4},
                {4, 4, 2, 8}};
        IMatrix matrixMock = new CoopMatrixMock(testMatrix);
        cooperativeAI = new CooperativeAI(matrixMock);
    }

    @Test
    public void insertRightTile() {
        cooperativeAI.createTile();
        assertEquals(4, testMatrix[2][2]);
    }

    @Test
    public void insertAnyTile() {
        assertTrue(cooperativeAI.createTile());
    }

}
