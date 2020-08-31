package game.model.intf;

import game.view.intf.ILeaderboardObserver;

import java.util.ArrayList;

/**
 * Interface for LeaderboardObservable, which notifies the view of the current leaderboard
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public interface ILeaderboardObservable {
    void registerObserver(ILeaderboardObserver observer);

    void removeObserver(ILeaderboardObserver observer);

    void resetObservers();

    void notifyObservers();

    ArrayList<Integer> getHighscores();

    ArrayList<Integer> getTiles();
}
