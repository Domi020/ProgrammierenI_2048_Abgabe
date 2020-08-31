package game.controller;

import game.model.AI.AIFactory;
import game.model.*;
import game.model.intf.*;
import game.model.leaderboard.SaveGameManager;
import game.model.leaderboard.ScoreManager;
import game.view.GUI.PlayPanel;
import game.view.RunController;
import game.view.intf.IGameBoard;
import game.view.intf.IScoreObserver;

/**
 * Class with static methods, which instantiates important objects at the start of the game,
 * connects them with the Observer pattern.
 *
 * @author Dominik Sauerer
 * @version 0.1.0
 */
public class GameManager {


    private static IGameBoard gameBoard;
    private static IMatrix matrix;
    private static IScoreObservable score;
    private static AI enemy;
    private static SaveGameManager saveGameManager;
    private static IBoardObservable observable;

    /**
     * Initializes a new game by creating the need matrix, mover and AI objects,
     * the gameboard view object, and connects all objects with Observer pattern.
     */
    public static void newGame() {
        if (gameBoard == null) return;
        initGame();
        enemy.createTile(); //first tile is created
    }

    public static void saveLeaderboard() {
        score.saveInLeaderboard();
    }

    private static void initGame() {
        gameBoard.reset();
        //Create new BoardObservable to notify the view about the game turns
        observable = new BoardObservable();
        observable.resetObservers();
        observable.registerObserver(gameBoard);
        gameBoard.setObservable(observable);

        //Create new matrix and mover object for game logic
        matrix = new Matrix(4);
        GameTurn.setGameField(matrix);
        matrix.registerObserver(gameBoard);
        IMover mover = new Mover(matrix);
        mover.setObservable(observable);
        GameTurn.setMover(mover);
        matrix.setObservable(observable);
        gameBoard.setObservable(matrix);

        //Create a new AI object
        enemy = AIFactory.getCurrentAI(matrix);
        enemy.setObservable(observable);
        GameTurn.setEnemy(enemy);

        //Create a new DebugAI for loadGame
        DebugAI debugAI = new DebugAI(matrix);
        debugAI.setObservable(observable);

        //Create a new ScoreManager to notify the view about the scores
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.setObservable(observable);
        observable.registerObserver(scoreManager);
        PlayPanel currentPlayPanel = RunController.getInstance().getViewManager().getPlayPanel();
        scoreManager.registerObserver(currentPlayPanel);
        currentPlayPanel.setObservable(scoreManager);
        score = scoreManager;

        saveGameManager = new SaveGameManager(matrix, debugAI);

        GameTurn.retry(); //set game to running
    }

    /**
     * Loads an existing game state
     */
    public static void loadGame() {
        initGame();
        saveGameManager.loadGame();
        enemy = AIFactory.getCurrentAI(matrix);
        enemy.setObservable(observable);
        GameTurn.setEnemy(enemy);
        score.loadScore(saveGameManager.getCurrentScore());
        score.notifyObservers();
    }

    public static void saveGame() {
        saveGameManager.saveGame(score.getCurrentScore());
    }

    public static IGameBoard getGameBoard() {
        return gameBoard;
    }

    public static IScoreObservable getScoreObservable() {
        return score;
    }

    public static void setGameBoard(IGameBoard board) {
        if (gameBoard == null) {
            gameBoard = board;
        }
    }

    public static void registerScoreObserver(IScoreObserver observer) {
        score.registerObserver(observer);
    }

}
