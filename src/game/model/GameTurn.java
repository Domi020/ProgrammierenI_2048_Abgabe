package game.model;

import game.Directions;
import game.controller.GameManager;
import game.model.intf.AI;
import game.model.intf.IMatrix;
import game.model.intf.IMover;

/**
 * Static class which represents a turn in the game, consisting of a player's move (move tiles)
 * and the spawn of a new tile from the AI
 *
 * @author Dominik Sauerer
 * @version 0.1.0
 */
public class GameTurn {

    private static IMatrix gameField;
    private static IMover mover;
    private static AI enemy;

    private static boolean running;

    /**
     * Perform a turn by moving a tile and creating a new one
     *
     * @param direction The direction in which the tile should be moved
     */
    public static void performTurn(Directions direction) {
        if (!running) return;
        mover.moveTiles(direction);
        try {
            Thread.sleep(250); //wait, so that the player's move and the tile creation can be distinguished
        } catch (Exception ignored) {
        }
        enemy.createTile();
        gameField.printMatrix();
        gameField.notifyObservers();
    }

    public static void setGameField(IMatrix field) {
        gameField = field;
    }

    public static void setMover(IMover move) {
        mover = move;
    }

    public static void setEnemy(AI enem) {
        enemy = enem;
    }

    public static void setLose() {
        running = false;
        GameManager.saveLeaderboard();
    }

    public static void retry() {
        running = true;
    }
}
