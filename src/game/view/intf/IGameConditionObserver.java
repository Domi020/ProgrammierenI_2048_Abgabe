package game.view.intf;

import game.model.intf.IGameConditionObservable;

public interface IGameConditionObserver {
    void refreshGameCondition();

    void setObservable(IGameConditionObservable matrix);
}
