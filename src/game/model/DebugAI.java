package game.model;

import game.model.intf.AI;
import game.model.intf.IBoardObservable;
import game.model.intf.IMatrix;

/**
 * Special AI class, which implements the AI interface, but is rather used to build a game
 * or for debugging. Should not be used for playing.
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public class DebugAI implements AI {
    private final IMatrix MATRIX;
    IBoardObservable observable;

    int currentX;
    int currentY;
    int currentValue;

    public DebugAI(IMatrix MATRIX) {
        this.MATRIX = MATRIX;
    }

    @Override
    public boolean createTile() {
        if (currentValue != 0) MATRIX.insertTile(currentX, currentY, currentValue);
        if (observable != null) observable.refreshValues(-1, -1, currentX, currentY, currentValue);
        if (observable != null) observable.notifyObservers();
        return true;
    }

    @Override
    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }

    public void setCoordinates(int x, int y, int value) {
        currentX = x;
        currentY = y;
        currentValue = value;
    }
}
