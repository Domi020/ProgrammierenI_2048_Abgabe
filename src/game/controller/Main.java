package game.controller;

import game.model.leaderboard.Leaderboards;
import game.view.DrawUtils;
import game.view.GUI.LeaderboardsPanel;
import game.view.Grid;
import game.view.RunController;
import game.view.ViewManager;

import javax.swing.*;

/**
 * Start point of the program
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 0.1.0
 */
public class Main {

    /**
     * Game start. The window is created, and some important objects are initialized
     */
    public static void main(String[] args) {
        //Create new game board (view)
        Grid board = new Grid(DrawUtils.WIDTH / 2 - Grid.BOARD_WIDTH / 2, DrawUtils.HEIGHT - Grid.BOARD_HEIGHT - 20);

        ViewManager.getInstance().init(board);
        GameManager.setGameBoard(board);

        //Draw window
        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(ViewManager.getInstance());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Prepare Leaderboards
        LeaderboardsPanel leaderboardsPanel = RunController.getInstance().getViewManager().getLeaderboardsPanel();
        leaderboardsPanel.setObservable(Leaderboards.getInstance());
        Leaderboards.getInstance().registerObserver(leaderboardsPanel);
        Leaderboards.getInstance().init();
    }
}
