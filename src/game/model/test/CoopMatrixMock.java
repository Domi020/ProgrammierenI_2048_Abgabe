package game.model.test;

import game.GameCondition;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;
import game.view.intf.IGameConditionObserver;

public class CoopMatrixMock implements IMatrix {

    private final int[][] matrix;

    public CoopMatrixMock(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public int[][] returnMatrix() {
        return matrix;
    }

    @Override
    public boolean insertTile(int row, int col, int value) {
        matrix[row][col] = value;
        return true;
    }

    @Override
    public int tileAt(int row, int col) {
        if (!(row > 3 || col > 3 || row < 0 || col < 0))
            return matrix[row][col];
        return -1;
    }

    @Override
    public int occupiedTiles() {
        return 0;
    }

    @Override
    public int returnMatrixSize() {
        return 0;
    }
    @Override
    public void printMatrix() {

    }

    @Override
    public boolean winCondition() {
        return false;
    }

    @Override
    public boolean loseCondition() {
        return false;
    }

    @Override
    public boolean checkLineForEqualTile(int row, int col, int value) {
        return false;
    }

    @Override
    public boolean checkColumnForEqualTile(int row, int col, int value) {
        return false;
    }

    @Override
    public void setObservable(IBoardObservable observable) {

    }

    @Override
    public void registerObserver(IGameConditionObserver observer) {

    }

    @Override
    public void removeObserver(IGameConditionObserver observer) {

    }

    @Override
    public void resetObservers() {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public GameCondition getGameCondition() {
        return null;
    }
}
