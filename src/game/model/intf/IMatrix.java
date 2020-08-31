package game.model.intf;

public interface IMatrix extends IGameConditionObservable {
    int[][] returnMatrix();

    boolean insertTile(int row, int col, int value);

    int tileAt(int row, int col);

    int occupiedTiles();

    int returnMatrixSize();

    void printMatrix();

    boolean winCondition();

    boolean loseCondition();

    boolean checkLineForEqualTile(int row, int col, int value);

    boolean checkColumnForEqualTile(int row, int col, int value);

    void setObservable(IBoardObservable observable);

}
