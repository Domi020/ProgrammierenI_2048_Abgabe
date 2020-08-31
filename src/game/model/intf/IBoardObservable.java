package game.model.intf;

import game.view.intf.IBoardObserver;

/**
 * Interface for BoardObservable, which notifies the view of every single move
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public interface IBoardObservable {
    void registerObserver(IBoardObserver observer);

    void removeObserver(IBoardObserver observer);

    void resetObservers();

    void notifyObservers();

    int[][] giveBoard();

    int giveFormerX();

    int giveFormerY();

    int giveNewX();

    int giveNewY();

    int giveMergedValue();

    void refreshValues(int formerX, int formerY, int newX, int newY, int mergedValue);
}
