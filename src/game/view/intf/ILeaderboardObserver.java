package game.view.intf;

import game.model.intf.ILeaderboardObservable;

public interface ILeaderboardObserver {
    void refreshScore();

    void setObservable(ILeaderboardObservable observable);
}
