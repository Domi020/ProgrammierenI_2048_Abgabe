package game.model;

import game.Directions;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;
import game.model.intf.IMover;

/**
 * Moves the single Tiles in the given Direction
 * moveTiles is the Main function.
 * An existing Matrix has to be given.
 *
 * @author Lukas Klein
 * @version 0.0.2
 */
public class Mover implements IMover {

    private final IMatrix matrix;

    public Mover(IMatrix matrix) {
        this.matrix = matrix;
    }

    private IBoardObservable observable;

    /**
     * Moves any Tile of the Whole Matrix in the given direction
     *
     * @param direction  Must be specified to clarify in which direction the Matrix should get moved
     * @return Return whether the operation has been successful of not
     */
    @Override
    public boolean moveTiles(Directions direction) {
        boolean hasBeenMoved = false;
        switch (direction) {
            case UP -> {
                for (int line = 0; line <= matrix.returnMatrixSize() - 1; line++) {
                    for (int column = 0; column <= matrix.returnMatrixSize() - 1; column++) {
                        if (matrix.tileAt(line, column) != 0) {
                            int savepointLine = line;
                            int mergedValue = -1;

                            while (!nextToTile(Directions.UP, line, column) || isSameTile(Directions.UP, line, column)) {
                                if (isSameTile(Directions.UP, line, column)) {
                                    mergedValue = mergeTiles(line - 1, column, line, column);
                                }

                                if (!nextToTile(Directions.UP, line, column)) {
                                    advance(Directions.UP, line, column);
                                    hasBeenMoved = true;
                                    line--;
                                }
                            }
                            if (observable != null) {
                                if (mergedValue == -1)
                                    observable.refreshValues(savepointLine, column, line, column, mergedValue);
                                else observable.refreshValues(savepointLine, column, line - 1, column, mergedValue);
                            }
                            line = savepointLine;
                            if (observable != null) observable.notifyObservers();
                        }
                    }
                }
                return hasBeenMoved;
            }
            case DOWN -> {
                for (int line = matrix.returnMatrixSize() - 1; line >= 0; line--) {
                    for (int column = 0; column < matrix.returnMatrixSize(); column++) {
                        if (matrix.tileAt(line, column) != 0) {
                            int savepointLine = line;
                            int mergedValue = -1;

                            while (!nextToTile(Directions.DOWN, line, column) || isSameTile(Directions.DOWN, line, column)) {
                                if (isSameTile(Directions.DOWN, line, column)) {
                                    mergedValue = mergeTiles(line + 1, column, line, column);
                                }

                                if (!nextToTile(Directions.DOWN, line, column)) {
                                    advance(Directions.DOWN, line, column);
                                    hasBeenMoved = true;
                                    line++;
                                }
                            }
                            if (observable != null) {
                                if (mergedValue == -1)
                                    observable.refreshValues(savepointLine, column, line, column, mergedValue);
                                else observable.refreshValues(savepointLine, column, line + 1, column, mergedValue);
                            }
                            line = savepointLine;
                            if (observable != null) observable.notifyObservers();
                        }
                    }
                }
                return hasBeenMoved;
            }
            case LEFT -> {
                for (int line = matrix.returnMatrixSize() - 1; line >= 0; line--) {
                    for (int column = 0; column <= matrix.returnMatrixSize() - 1; column++) {
                        if (matrix.tileAt(line, column) != 0) {
                            int savepointColumn = column;
                            int mergedValue = -1;

                            while (!nextToTile(Directions.LEFT, line, column) || isSameTile(Directions.LEFT, line, column)) {
                                if (isSameTile(Directions.LEFT, line, column)) {
                                    mergedValue = mergeTiles(line, column - 1, line, column);
                                }

                                if (!nextToTile(Directions.LEFT, line, column)) {
                                    advance(Directions.LEFT, line, column);
                                    column--;
                                    hasBeenMoved = true;
                                }
                            }
                            if (observable != null) {
                                if (mergedValue == -1)
                                    observable.refreshValues(line, savepointColumn, line, column, mergedValue);
                                else observable.refreshValues(line, savepointColumn, line, column - 1, mergedValue);
                            }
                            column = savepointColumn;
                            if (observable != null) observable.notifyObservers();
                        }
                    }
                }
                return hasBeenMoved;
            }
            case RIGHT -> {
                for (int line = matrix.returnMatrixSize() - 1; line >= 0; line--) {
                    for (int column = matrix.returnMatrixSize() - 1; column >= 0; column--) {
                        if (matrix.tileAt(line, column) != 0) {

                            int savepointColumn = column;
                            int mergedValue = -1;

                            while (!nextToTile(Directions.RIGHT, line, column) || isSameTile(Directions.RIGHT, line, column)) {
                                if (isSameTile(Directions.RIGHT, line, column))
                                    mergedValue = mergeTiles(line, column + 1, line, column);

                                if (!nextToTile(Directions.RIGHT, line, column)) {
                                    advance(Directions.RIGHT, line, column);
                                    column++;
                                    hasBeenMoved = true;
                                }
                            }
                            if (observable != null) {
                                if (mergedValue == -1)
                                    observable.refreshValues(line, savepointColumn, line, column, mergedValue);
                                else observable.refreshValues(line, savepointColumn, line, column + 1, mergedValue);
                            }
                            column = savepointColumn;
                            if (observable != null) observable.notifyObservers();
                        }
                    }
                }
                return hasBeenMoved;
            }
        }
        return hasBeenMoved;
    }


    /**
     * @param direction  The direction which is going to be checked.
     * @param line  The line of the current Tile which is being checked to be next to one another.
     * @param column  The column of the current Tile which is being checked to be next to one another.
     */
    @Override
    public boolean nextToTile(Directions direction, int line, int column) {
        return switch (direction) {
            case UP -> (matrix.tileAt(line - 1, column) != 0);
            case DOWN -> (matrix.tileAt(line + 1, column) != 0);
            case LEFT -> (matrix.tileAt(line, column - 1) != 0);
            case RIGHT -> (matrix.tileAt(line, column + 1) != 0);
        };
    }

    /**
     * @param direction  The direction which is getting checked.
     * @param line  The line of the current Tile.
     * @param column  The column of the current Tile.
     */
    @Override
    public boolean isSameTile(Directions direction, int line, int column) {

        return switch (direction) {
            case UP -> (matrix.tileAt(line - 1, column) == matrix.tileAt(line, column));
            case DOWN -> (matrix.tileAt(line + 1, column) == matrix.tileAt(line, column));
            case LEFT -> (matrix.tileAt(line, column - 1) == matrix.tileAt(line, column));
            case RIGHT -> (matrix.tileAt(line, column + 1) == matrix.tileAt(line, column));
        };
    }

    /**
     * @param lineFirst  The line of the Tile which is going to get bigger.
     * @param columnFirst  The Column of the Tile which is going to get bigger.
     * @param lineSecond  The line of the Tile which is getting "deleted"/merged into the other one.
     * @param columnSecond  The column of the Tile which is getting "deleted"/merged into the other one.
     */
    private int mergeTiles(int lineFirst, int columnFirst, int lineSecond, int columnSecond) {

        int mergedTile = matrix.tileAt(lineFirst, columnFirst) + matrix.tileAt(lineSecond, columnSecond);
        matrix.insertTile(lineSecond, columnSecond, 0);
        matrix.insertTile(lineFirst, columnFirst, mergedTile);
        return mergedTile;
    }


    /**
     * @param direction In which direction the Tile is moved.
     * @param line      The line of the current Tile which is to be moved.
     * @param column    The column of the current Tile which is to be moved.
     */
    private void advance(Directions direction, int line, int column) {

        int toBeMoved = matrix.tileAt(line, column);
        matrix.insertTile(line, column, 0);

        switch (direction) {
            case UP:
                matrix.insertTile(line - 1, column, toBeMoved);
                return;

            case DOWN:
                matrix.insertTile(line + 1, column, toBeMoved);
                return;

            case LEFT:
                matrix.insertTile(line, column - 1, toBeMoved);
                return;

            case RIGHT:
                matrix.insertTile(line, column + 1, toBeMoved);
                return;
            default:
        }
    }
    @Override
    public void setObservable(IBoardObservable observable){
        this.observable = observable;
    }
}