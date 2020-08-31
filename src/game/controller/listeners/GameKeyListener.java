package game.controller.listeners;

import game.Directions;
import game.model.GameTurn;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Listener, which gets keyboard input of the user on the GameBoard and performs the wished move
 *
 * @author Dominik Sauerer
 * @version 0.1.0
 */
public class GameKeyListener extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case(KeyEvent.VK_DOWN):
                GameTurn.performTurn(Directions.DOWN);
                break;
            case(KeyEvent.VK_UP):
                GameTurn.performTurn(Directions.UP);
                break;
            case(KeyEvent.VK_LEFT):
                GameTurn.performTurn(Directions.LEFT);
                break;
            case(KeyEvent.VK_RIGHT):
                GameTurn.performTurn(Directions.RIGHT);
                break;
            default:
                break;
        }
    }
}
