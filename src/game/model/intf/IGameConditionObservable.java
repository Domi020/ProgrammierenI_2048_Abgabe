package game.model.intf;

import game.GameCondition;
import game.view.intf.IGameConditionObserver;

/**
 * Interface of GameConditionObservable, which notifies the view of the current game condition
 * (running, game won, or game lost)
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public interface IGameConditionObservable {
    void registerObserver(IGameConditionObserver observer);

    void removeObserver(IGameConditionObserver observer);

    void resetObservers();

    void notifyObservers();

    GameCondition getGameCondition();
}
