package game.model;

import game.model.intf.IBoardObservable;
import game.view.intf.IBoardObserver;

import java.util.ArrayList;

/**
 * Help class which gets data from Matrix, Mover and AI and notifies the view about it
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public class BoardObservable implements IBoardObservable {

    private final ArrayList<IBoardObserver> observers = new ArrayList<>();

    private int formerX;
    private int formerY;
    private int newX;
    private int newY;
    private int mergedValue;

    @Override
    public void registerObserver(IBoardObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IBoardObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void resetObservers() {
        for (IBoardObserver observer : observers) {
            removeObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (IBoardObserver o : observers) {
            o.refresh();
        }
    }

    @Override
    public int[][] giveBoard() {
        return new int[0][];
    }

    @Override
    public int giveFormerX() {
        return formerX;
    }

    @Override
    public int giveFormerY() {
        return formerY;
    }

    @Override
    public int giveNewX() {
        return newX;
    }

    @Override
    public int giveNewY() {
        return newY;
    }

    @Override
    public int giveMergedValue(){
        return mergedValue;
    }

    @Override
    public void refreshValues(int formerX, int formerY, int newX, int newY, int mergedValue){
        this.formerX = formerX;
        this.formerY = formerY;
        this.newX = newX;
        this.newY = newY;
        this.mergedValue = mergedValue;
    }
}
