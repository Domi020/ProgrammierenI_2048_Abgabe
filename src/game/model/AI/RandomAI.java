package game.model.AI;

import game.model.intf.AI;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;

import java.util.Random;

/**
 * Creates a random AI, which places 2/4s every move
 * Is able to create a new Tile with the value 2 or 4
 * at random locations
 *
 * @author Dominik Sauerer | Aaron Vermeulen
 * @version 1.0.0
 */
public class RandomAI implements AI {

    private final IMatrix MATRIX;
    private final Random RANDOM;


    public RandomAI(IMatrix MATRIX) {
        this.MATRIX = MATRIX;
        RANDOM = new Random();
    }

    private IBoardObservable observable;

    /**
     * @return Returns a boolean whether a tile has been created successfully
     **/
    @Override
    public boolean createTile() {
        int higherInsertableValue = 4;
        int lowerInsertableValue = 2;
        int MATRIX_SIZE = 4;
        boolean[][] checked = new boolean[MATRIX_SIZE][MATRIX_SIZE];

        int checkedCounter = 0;
        while (checkedCounter < (MATRIX_SIZE * MATRIX_SIZE)) {
            int row = RANDOM.nextInt(MATRIX_SIZE);
            int col = RANDOM.nextInt(MATRIX_SIZE);
            int value;

            if (RANDOM.nextInt(100) < 90)    // 90% Chance of a 2, 10% Chance of a 4
                value = lowerInsertableValue;
            else
                value = higherInsertableValue;

            if (checkIfFree(row, col)) {
                if (observable != null) observable.refreshValues(-1, -1, row, col, value);
                MATRIX.insertTile(row, col, value);
                if (observable != null) observable.notifyObservers();
                return true;
            }
            if (!checked[row][col]) {
                checked[row][col] = true;
                checkedCounter++;
            }
        }
        return false;
    }

    /**
     * Checks if a tile is free (tile = 0)
     *
     * @param row Row of the tile you want to check
     * @param col Column of the tile you want to check
     * @return Returns a boolean whether the tile is free/usable
     */
    private boolean checkIfFree(int row, int col) {
        return (MATRIX.tileAt(row, col) == 0);
    }

    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }
}
