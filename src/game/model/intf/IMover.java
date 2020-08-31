package game.model.intf;


import game.Directions;

public interface IMover {

    boolean moveTiles(Directions directions);

    boolean nextToTile(Directions direction, int line, int column);

    boolean isSameTile(Directions direction, int line, int column);

    void setObservable(IBoardObservable observable);
}
