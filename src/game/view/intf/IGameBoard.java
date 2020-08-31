package game.view.intf;

public interface IGameBoard extends IGameConditionObserver, IBoardObserver {
    void reset();
}
