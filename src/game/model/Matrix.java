package game.model;

import game.GameCondition;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;
import game.view.intf.IGameConditionObserver;

import java.util.ArrayList;

/**
 * Creates a Matrix with various sizes.
 * Is able to return the Matrix, insert Tiles into the Matrix
 * and return the Tile at a given index.
 *
 * @author Lukas Klein, Dominik Sauerer
 * @version 0.0.2
 */
public class Matrix implements IMatrix{

    private int[][] matrix;
    private int matrixSize;

    private final ArrayList<IGameConditionObserver> observers = new ArrayList<>();
    private IBoardObservable observable;

    public Matrix(int matrixSize) { //Standard-Konstruktor für Spielfeld
        create(matrixSize);
    }

    public Matrix(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];
        this.matrixSize = matrix.length;
        for (int line = 0; line < matrixSize; line++)
            System.arraycopy(matrix[line], 0, this.matrix[line], 0, matrixSize);
    }

    private void create(int size) {
        this.matrixSize = size;
        this.matrix = new int[size][size];
        for (int line = 0; line < size; line++)
            for (int column = 0; column < size; column++)
                matrix[line][column] = 0;
    }

    /**
     * @return Returns the whole Matrix as int[][]
     */
    @Override
    public int[][] returnMatrix() {
        return this.matrix;
    }

    /**
     * Inserts a Tile at a given position is the initialized Matrix
     *
     * @param row  The row in which the tile is going to be added
     * @param col  The column in which the tile is going to be added
     * @param value  The value of the Tile
     *
     * @return Returns a boolean if the insertion has been successful.
     */
    @Override
    public boolean insertTile(int row, int col, int value) {
        if (checkValidity(row, col)) {
            matrix[row][col] = value;
            if (observable != null) observable.refreshValues(-1, -1, row, col, value);
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a Tile at a certain position
     *
     * @param row  The line of the place which is going to be checked
     * @param col  The column of the place which is going to be checked
     *
     * @return If successful returns the value of the Tile. If not -1 is returned.
     */
    @Override
    public int tileAt(int row, int col) {
        if (checkValidity(row, col))
            return matrix[row][col];
        return -1;
    }

    /**
     * Checks if the given Tile is in range of the Matrix
     *
     * @param row  Line of the place which is going to be checked
     * @param col  Column of the place which is going to be checked
     *
     * @return Returns a boolean whether the Tile is valid or not
     */
    private boolean checkValidity(int row, int col) {
        return !(row > matrixSize - 1 || col > matrixSize - 1 || row < 0 || col < 0);
    }

    /**
     * @return Returns the size of the Matrix.
     */
    @Override
    public int returnMatrixSize() {
        return matrixSize;
    }

    /**
     * @return Returns how many Tiles are currently occupied in the Matrix.
     **/
    @Override
    public int occupiedTiles() {
        int howManyTilesAreOccupied = 0;
        for (int line = 0; line < matrixSize; line++)
            for (int column = 0; column < matrixSize; column++)
                if (matrix[line][column] != 0)
                    howManyTilesAreOccupied += 1;
        return howManyTilesAreOccupied;
    }

    /**
     * Prints out the whole Matrix
     */
    @Override
    public void printMatrix(){
        for(int row = 0; row < matrixSize; row++){
            for(int col = 0; col < matrixSize; col++){
                System.out.print(matrix[row][col] + ", ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    /**
     * Checks if there is a 2048-Tile in the Matrix
     *
     * @return Returns a boolean which indicates whether the Win Condition is reached or not
     */
    @Override
    public boolean winCondition(){
        for(int row = 0; row < matrixSize; row++){
            for(int col = 0; col < matrixSize; col++){
                if (matrix[row][col] == 2048){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the game is lost
     *
     * @return Returns a boolean which indicates whether the Lose Condition is reached or not
     */
    @Override
    public boolean loseCondition(){
        for(int row = 0; row < matrixSize; row++){
            for(int col = 0; col < matrixSize; col++){
                if (matrix[row][col] != 0 && !immovable(row, col)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean immovable(int line, int column){

        if((tileAt(line - 1, column) == 0)||
                (tileAt(line + 1, column) == 0) ||
                (tileAt(line, column - 1) == 0) ||
                (tileAt(line, column + 1) == 0))
            return false;

        return (tileAt(line - 1, column) != tileAt(line, column)) &&
                (tileAt(line + 1, column) != tileAt(line, column)) &&
                (tileAt(line, column - 1) != tileAt(line, column)) &&
                (tileAt(line, column + 1) != tileAt(line, column));
    }

    @Override
    public void registerObserver(IGameConditionObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IGameConditionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void resetObservers() {
        for (IGameConditionObserver observer : observers) {
            removeObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (IGameConditionObserver observer : observers) {
            observer.refreshGameCondition();
        }
    }

    @Override
    public GameCondition getGameCondition() {
        if (winCondition()) {
            GameTurn.setLose();
            return GameCondition.WON;
        }
        if (loseCondition()) {
            GameTurn.setLose();
            return GameCondition.LOST;
        }
        return GameCondition.RUNNING;
    }

    @Override
    public boolean checkLineForEqualTile(int row, int col, int value) {
        for (int i = col + 1; i < matrixSize; i++) {
            if (matrix[row][i] == value) {
                return true;
            } else if (matrix[row][i] > 0) {
                break;
            }
        }
        for (int i = col - 1; i > -1; i++) {
            if (matrix[row][i] == value) {
                return true;
            } else if (matrix[row][i] > 0) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean checkColumnForEqualTile(int row, int col, int value) {
        for (int i = row + 1; i < matrixSize; i++) {
            if (matrix[i][col] == value) {
                return true;
            } else if (matrix[i][col] > 0) {
                break;
            }
        }
        for (int i = row - 1; i > -1; i++) {
            if (matrix[i][col] == value) {
                return true;
            } else if (matrix[i][col] > 0) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }
}