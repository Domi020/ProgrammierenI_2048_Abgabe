package game.model.leaderboard;

import game.model.intf.IBoardObservable;
import game.model.intf.IScoreObservable;
import game.view.intf.IBoardObserver;
import game.view.intf.IScoreObserver;

import java.util.ArrayList;

/**
 * Help class a observable/observer between the leaderboard and the play panel/score panel
 * to update the current scores
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public class ScoreManager implements IBoardObserver, IScoreObservable {
    private IBoardObservable observable;

    private final ArrayList<IScoreObserver> observers = new ArrayList<>();

    private int currentScore = 0;
    private int highestTile = 0;

    @Override
    public void refresh() {
        int merged = observable.giveMergedValue();
        int oldX = observable.giveFormerX();
        if (merged > -1 && oldX != -1) {
            currentScore += merged;
        }
        if (merged > highestTile) highestTile = merged;
        notifyObservers();
    }

    @Override
    public void setObservable(IBoardObservable matrix) {
        observable = matrix;
    }

    @Override
    public void registerObserver(IScoreObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IScoreObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void resetObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        for (IScoreObserver observer : observers) {
            observer.refreshScore();
        }
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public int getHighScore() {
        Leaderboards leaderboards = Leaderboards.getInstance();
        return leaderboards.getHighScore();
    }

    @Override
    public void loadScore(int score) {
        if (currentScore == 0)
            currentScore = score;
    }

    @Override
    public void saveInLeaderboard() {
        Leaderboards leaderboards = Leaderboards.getInstance();
        leaderboards.init();
        leaderboards.addScore(currentScore);
        leaderboards.addTile(highestTile);
        leaderboards.saveScores();
    }
}
