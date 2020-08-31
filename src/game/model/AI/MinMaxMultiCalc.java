package game.model.AI;

import game.model.Matrix;
import game.model.Mover;
import game.model.intf.IMatrix;
import game.model.intf.IMover;

import static game.Directions.*;

/**
 * Thread which is used by MinMaxMulti
 *
 * @author Dominik Sauerer, Aaron Vermeulen
 * @version 0.1.0
 */
public class MinMaxMultiCalc extends Thread {
    private final int MATRIX_SIZE = 4;
    private IMover currentMover;
    private final IMatrix matrix;
    private int result;
    private final int row;
    private final int col;

    public MinMaxMultiCalc(IMover currentMover, IMatrix matrix, int row, int col) {
        this.currentMover = currentMover;
        this.matrix = matrix;
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        result = PlayerTurn(matrix, 1);
    }

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

    public int getResult() {
        return result;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
