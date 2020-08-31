package game.model.test;

import game.model.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatrixTest {

    private Matrix testableMatrix;
    private Matrix alternativeTestableMatrix;

    @BeforeEach
    private void init(){
        int[][] matrixPattern = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        this.testableMatrix = new Matrix(matrixPattern);

        int[][] alternativeMatrixPattern = {
                { 2,     4,    8,  16},
                {32,    64,  128, 256},
                {512, 1024, 2048,   2},
                {4,      8,   16,  32}
        };
        this.alternativeTestableMatrix = new Matrix(alternativeMatrixPattern);
    }

    @Test
    public void correctCreate(){
        assertThat(testableMatrix.tileAt(3,3), is(0));
        assertThat(testableMatrix.tileAt(4,4), is(-1));
    }

    @Test
    public void returnMatrixSize(){
        assertThat(testableMatrix.returnMatrixSize(), is(4));
    }

    @Test
    public void insertTile(){
        testableMatrix.insertTile(2,2, 2048);
        assertThat(testableMatrix.tileAt(2,2), is(2048));
    }

    @Test
    public void occupiedTiles(){
        testableMatrix.insertTile(0,0,2);
        testableMatrix.insertTile(1,1, 4);
        testableMatrix.insertTile(2,2,8);
        testableMatrix.insertTile(3,3,16);

        assertThat(testableMatrix.occupiedTiles(), is(4));
    }

    @Test
    public void winCondition(){
        boolean win = alternativeTestableMatrix.winCondition();
        assertThat(win, is(true));
    }

    @Test
    public void loseCondition(){
        boolean lose = alternativeTestableMatrix.loseCondition();
        assertThat(lose, is(true));
    }

}
