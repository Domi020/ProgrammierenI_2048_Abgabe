package game.view.intf;

import game.model.intf.IScoreObservable;

public interface IScoreObserver {
    void refreshScore();

    void setObservable(IScoreObservable observable);
}
