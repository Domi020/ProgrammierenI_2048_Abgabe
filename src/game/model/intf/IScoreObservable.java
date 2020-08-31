package game.model.intf;

import game.view.intf.IScoreObserver;

/**
 * Interface for ScoreObservable, which notifies the view (the play panel) of the current
 * highscore
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public interface IScoreObservable {
    void registerObserver(IScoreObserver observer);

    void removeObserver(IScoreObserver observer);

    void resetObservers();

    void notifyObservers();

    int getCurrentScore();

    void loadScore(int score);

    void saveInLeaderboard();

    int getHighScore();

}
