package game.model.test;

import game.Directions;
import game.model.Matrix;
import game.model.Mover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoverTest {
    private Matrix testableMatrix;

    @BeforeEach
    private void init() {
        int[][] matrixPattern = {
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        };
        Matrix matrix = new Matrix(matrixPattern);
        this.testableMatrix = matrix;
    }


    @Test
    public void moveTilesUp() {
        Mover mover = new Mover(testableMatrix);
        assertTrue(mover.moveTiles(Directions.UP));
        assertThat(testableMatrix.tileAt(0, 1), is(4));
        assertThat(testableMatrix.tileAt(0, 2), is(4));
    }

    @Test
    public void moveTilesDown() {
        Mover mover = new Mover(testableMatrix);
        assertTrue(mover.moveTiles(Directions.DOWN));
        assertThat(testableMatrix.tileAt(3, 1), is(4));
        assertThat(testableMatrix.tileAt(3, 2), is(4));
    }

    @Test
    public void moveTilesLeft() {
        Mover mover = new Mover(testableMatrix);
        assertTrue(mover.moveTiles(Directions.LEFT));
        assertThat(testableMatrix.tileAt(1, 0), is(4));
        assertThat(testableMatrix.tileAt(2, 0), is(4));
    }

    @Test
    public void moveTilesRight() {
        Mover mover = new Mover(testableMatrix);
        assertTrue(mover.moveTiles(Directions.RIGHT));
        assertThat(testableMatrix.tileAt(1, 3), is(4));
        assertThat(testableMatrix.tileAt(2, 3), is(4));

    }
}
