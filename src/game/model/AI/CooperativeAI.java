package game.model.AI;

import game.model.intf.AI;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;

/**
 * @author Dominik Sauerer | Aaron Vermeulen
 * @version 0.1.0
 * Cooperative AI, which places 2/4s every move.
 * Is able to create a new Tile with the value 2 or 4
 * at the best possible locations for the player
 */
public class CooperativeAI implements AI {

    private final IMatrix MATRIX;

    private IBoardObservable observable;

    /**
     * Creates a CooperativeAI based on the given IMatrix
     *
     * @param MATRIX IMatrix, in which the AI performs their tile creation
     */
    public CooperativeAI(IMatrix MATRIX) {
        this.MATRIX = MATRIX;
    }

    /**
     * Creates a new tile in MATRIX
     *
     * @return returns a boolean whether a tile has been created successfully
     */
    @Override
    public boolean createTile() {
        final int HIGHER_INSERTABLE_VALUE = 4;
        final int LOWER_INSERTABLE_VALUE = 2;

        if (tryInsertNextTo(HIGHER_INSERTABLE_VALUE) || tryInsertNextTo(LOWER_INSERTABLE_VALUE))
            return true;
        int MATRIX_SIZE = 4;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (MATRIX.tileAt(i, j) == 0) {
                    MATRIX.insertTile(i, j, 2);
                    if (observable != null) observable.refreshValues(-1, -1, i, j, 2);
                    if (observable != null) observable.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Searches for any tile with the same value as given and tries to insert it next to it
     *
     * @param value The tile value you are searching for and want to place a tile next to
     * @return Returns a boolean whether the value was successfully found
     */
    private boolean tryInsertNextTo(int value) {

        int MATRIX_SIZE = 4;
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                if (MATRIX.tileAt(row, col) == value)
                    if (insertNeighbour(value, row, col))
                        return true;
            }
        }
        return false;
    }

    /**
     * Tries to insert a new tile next to the given tile
     *
     * @param value The value of the tile you want to place
     * @param row   Column of the tile you want to place next to
     * @param col   Row of the tile you want to place next to
     * @return Returns a boolean whether the value was successfully inserted
     */
    private boolean insertNeighbour(int value, int row, int col) {

        if (checkIfFree(row + 1, col)) {
            MATRIX.insertTile(row + 1, col, value);
            if (observable != null) observable.refreshValues(-1, -1, row + 1, col, value);
            if (observable != null) observable.notifyObservers();
            return true;
        } else if (checkIfFree(row - 1, col)) {
            MATRIX.insertTile(row - 1, col, value);
            if (observable != null) observable.refreshValues(-1, -1, row - 1, col, value);
            if (observable != null) observable.notifyObservers();
            return true;
        } else if (checkIfFree(row, col + 1)) {
            MATRIX.insertTile(row, col + 1, value);
            if (observable != null) observable.refreshValues(-1, -1, row, col + 1, value);
            if (observable != null) observable.notifyObservers();
            return true;
        } else if (checkIfFree(row, col - 1)) {
            MATRIX.insertTile(row, col - 1, value);
            if (observable != null) observable.refreshValues(-1, -1, row, col - 1, value);
            if (observable != null) observable.notifyObservers();
            return true;
        } else
            return false;
    }

    /**
     * Uses IMatrix methods to check, if a tile is free
     *
     * @param row Column of the tile you want to check
     * @param col Row of the tile you want to check
     * @return Returns a boolean whether the tile is free/usable
     */
    private boolean checkIfFree(int row, int col) {

        return (MATRIX.tileAt(row, col) == 0);
    }

    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }
}