package game.model.AI.test;

import game.model.AI.RandomAI;
import game.model.intf.IMatrix;
import game.model.test.RandomMatrixMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomAITest {
    private RandomAI randomAi;
    private int[][] innerMatrix;

    @BeforeEach
    public void init() {
        innerMatrix = new int[][]
                {{1, 2, 4, 3},
                {1, 2, 3, 0},
                {1, 2, 4, 3},
                {1, 2, 4, 3}};
        IMatrix game = new RandomMatrixMock(innerMatrix);
        randomAi = new RandomAI(game);
    }

    @Test
    public void insertRightTile() {
        randomAi.createTile();

        assertThat(innerMatrix[1][3], anyOf(is(2), is(4)));
    }

    @Test
    public void insertAnyTile() {
        assertTrue(randomAi.createTile());
    }

    @Test
    public void checkRandomness() {
        int TwoCounter = 0;
        int FourCounter = 0;

        for (int i = 0; i < 1000; i++) {
            innerMatrix[1][3] = 0;
            randomAi.createTile();
            if (innerMatrix[1][3] == 4)
                FourCounter++;
            else if (innerMatrix[1][3] == 2)
                TwoCounter++;
        }
        assertTrue(TwoCounter < 925 && TwoCounter > 875);
        assertTrue(FourCounter < 125 && FourCounter > 75);
    }

}
