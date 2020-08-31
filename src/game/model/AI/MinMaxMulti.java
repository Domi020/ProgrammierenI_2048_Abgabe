package game.model.AI;

import game.model.Matrix;
import game.model.Mover;
import game.model.intf.AI;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;
import game.model.intf.IMover;

import java.util.ArrayList;

/**
 * Another MinMaxAI with the same business logic, but using multithreading for better performance
 *
 * @author Dominik Sauerer, Aaron Vermeulen
 * @version 0.1.0
 */
public class MinMaxMulti extends Thread implements AI {

    private final IMatrix MATRIX;

    private IBoardObservable observable;

    private final ArrayList<MinMaxMultiCalc> calcs = new ArrayList<>();

    /**
     * Creates a MinMaxAI based on the given IMatrix
     *
     * @param matrix IMatrix, in which the AI performs their tile creation
     */
    public MinMaxMulti(IMatrix matrix) {
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
        int maxTurn = -1;
        int row = -1, col = -1;

        int MATRIX_SIZE = 4;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (MATRIX.tileAt(i, j) == 0) {
                    IMatrix matrix = new Matrix(MATRIX.returnMatrix());
                    matrix.insertTile(i, j, 2);
                    IMover mover = new Mover(matrix);
                    MinMaxMultiCalc calc = new MinMaxMultiCalc(mover, matrix, i, j);
                    calcs.add(calc);
                    calc.start();
                }
            }
        }
        for (int i = 0; i < calcs.size(); i++) {
            MinMaxMultiCalc calc = calcs.get(i);
            try {
                calc.join();
            } catch (Exception e) {
                i--;
                continue;
            }
            int x = calc.getResult();
            if (x > maxTurn) {
                maxTurn = x;
                row = calc.getRow();
                col = calc.getCol();
            }

        }
        calcs.clear();
        if (observable != null) observable.refreshValues(-1, -1, row, col, 2);
        boolean worked = MATRIX.insertTile(row, col, 2);
        if (worked) {
            if (observable != null) observable.notifyObservers();
            return true;
        }
        return false;
    }

    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }
}
