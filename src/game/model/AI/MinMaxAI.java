package game.model.AI;

import game.model.Matrix;
import game.model.Mover;
import game.model.intf.AI;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;
import game.model.intf.IMover;

import static game.Directions.*;

/**
 * MinMax AI, which places 2s every move by calculating a Minimax-Algorithm.
 * Places in favor of the AI (= player should lose the game)
 *
 * @author Dominik Sauerer | Aaron Vermeulen
 * @version 0.2.0
 */
public class MinMaxAI implements AI {

    private final IMatrix MATRIX;
    private final int MATRIX_SIZE = 4;

    private IMover currentMover;

    private IBoardObservable observable;

    /**
     * Creates a MinMaxAI based on the given IMatrix
     *
     * @param matrix IMatrix, in which the AI performs their tile creation
     */
    public MinMaxAI(IMatrix matrix) {
        this.MATRIX = matrix;
    }

    /**
     * Creates a new tile in MATRIX by performing the Minimax algorithm and calculating
     * the best alternative for the AI
     *
     * @return true, if insertion of AI was successful; false, if not
     */
    @Override
    public boolean createTile() {
        IMatrix currentMatrix = new Matrix(MATRIX.returnMatrix());
        currentMover = new Mover(currentMatrix);
        int maxTurn = -1;
        int row = -1, col = -1;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (currentMatrix.tileAt(i, j) == 0) {
                    currentMatrix.insertTile(i, j, 2);
                    int x = PlayerTurn(currentMatrix, 1);
                    if (x > maxTurn) {
                        maxTurn = x;
                        row = i;
                        col = j;
                    }
                   currentMatrix = new Matrix(MATRIX.returnMatrix());
                }
            }
        }
        if(observable != null) observable.refreshValues(-1,-1,row,col,2);
        boolean worked = MATRIX.insertTile(row, col, 2);
        if (worked) {
            if (observable != null) observable.notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Simulates an AI move (insertion of one tile) as part of the Minimax algorithm
     *
     * @param currentMatrix IMatrix, in which the AI performs their tile creation
     * @param turn          number of current turn in algorithm (to check, if the algorithm should be aborted)
     * @return rating of best possible game situation for the previous step
     */
    private int KITurn(IMatrix currentMatrix, int turn) {
        int MAX_TURNS = 4;
        if (turn > MAX_TURNS) {
            return rateSituation(currentMatrix);
        }
        IMatrix backup = new Matrix(currentMatrix.returnMatrix());
        int maxTurn = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (currentMatrix.tileAt(i, j) == 0) {
                    currentMatrix.insertTile(i, j, 2);
                    int x = PlayerTurn(currentMatrix, turn);
                    if (x > maxTurn) {
                        maxTurn = x;
                    }
                    currentMatrix = new Matrix(backup.returnMatrix());
                }
            }
        }
        return maxTurn;
    }

    /**
     * Simulates an player move (move tiles) as part of the Minimax algorithm
     *
     * @param currentMatrix IMatrix, in which the player moves the tiles
     * @param turn          number of current turn in algorithm (to check, if the algorithm should be aborted)
     * @return rating of worst possible game situation for the previous step
     */
    private int PlayerTurn(IMatrix currentMatrix, int turn) {
        IMatrix backup = new Matrix(currentMatrix.returnMatrix());
        int minTurn = MATRIX_SIZE * MATRIX_SIZE + 1;

        if (currentMover.moveTiles(UP)) {
            int x = KITurn(currentMatrix, turn + 1);
            if (x < minTurn) {
                minTurn = x;
            }
            currentMatrix = new Matrix(backup.returnMatrix());
            currentMover = new Mover(currentMatrix);
        }
        if (currentMover.moveTiles(DOWN)) {
            int x = KITurn(currentMatrix, turn + 1);
            if (x < minTurn) {
                minTurn = x;
            }
            currentMatrix = new Matrix(backup.returnMatrix());
            currentMover = new Mover(currentMatrix);
        }
        if (currentMover.moveTiles(LEFT)) {
            int x = KITurn(currentMatrix, turn + 1);
            if (x < minTurn) {
                minTurn = x;
            }
            currentMatrix = new Matrix(backup.returnMatrix());
            currentMover = new Mover(currentMatrix);
        }
        if (currentMover.moveTiles(RIGHT)) {
            int x = KITurn(currentMatrix, turn + 1);
            if (x < minTurn) {
                minTurn = x;
            }
            currentMatrix = new Matrix(backup.returnMatrix());
            currentMover = new Mover(currentMatrix);
        }
        return minTurn;
    }

    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }

    /**
     * Rates the current game situation (= the tiles which currently are on the board) and gives points
     * depending on how bad the situation is for the player
     *
     * @param matrix the current game state
     * @return a rating, how bad the situation is for the player from 1 - MATRIXSIZE^2
     */
    private int rateSituation(IMatrix matrix) {
        int rating = 0;
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                if (matrix.checkColumnForEqualTile(row, col, matrix.tileAt(row, col)) ||
                        matrix.checkLineForEqualTile(row, col, matrix.tileAt(row, col))) {
                    continue;
                }
                rating++;
            }
        }
        return rating;
    }
}
