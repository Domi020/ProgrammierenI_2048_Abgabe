package game.view.intf;

import game.model.intf.IBoardObservable;

public interface IBoardObserver {
    void refresh();

    void setObservable(IBoardObservable matrix);
}
