package game.view;

import game.GameCondition;
import game.model.intf.IBoardObservable;
import game.model.intf.IGameConditionObservable;
import game.view.intf.IGameBoard;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the grid on which the game is played
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 1.0.0
 */
public class Grid implements IGameBoard {

    public static final int ROWS = 4;
    public static final int COLS = 4;
    private Tile[][] board;
    private boolean dead;
    private boolean won;
    private static final int SPACING = 10;
    private final BufferedImage gameBoard;
    private final int x;
    private final int y;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    private int saveCount = 0;

    private IBoardObservable observable;
    private IGameConditionObservable conditionObservable;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        createBoardImage();
    }

    public void reset() {
        board = new Tile[ROWS][COLS];
        dead = false;
        won = false;
        saveCount = 0;
    }

    /**
     * Draws the grid without tiles
     */
    private void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }
    }

    /**
     * Update method, which is called regularly by the RunController and updates the current tiles
     */
    public void update() {
        saveCount++;
        if (saveCount >= 120) {
            saveCount = 0;
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.update();
                resetPosition(current, row, col);
                if (current.getValue() == 2048) {
                    setWon(true);
                }
            }
        }
    }

    /**
     * Draw method, which draws the complete grid with tiles
     *
     * @param g The Graphics2D object on which should be drawn
     */
    public void render(Graphics2D g) {
        BufferedImage finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g2d.drawImage(gameBoard, 0, 0, null);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.render(g2d);
            }
        }
        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    /**
     * Sets positions for moving animation of tiles
     *
     * @param tile Tile which should be animated
     * @param row  The row where to be moved
     * @param col  The column where to be moved
     */
    private void resetPosition(Tile tile, int row, int col) {
        if (tile == null) return;

        int x = getTileX(col);
        int y = getTileY(row);

        int distX = tile.getX() - x;
        int distY = tile.getY() - y;

        if (Math.abs(distX) < Tile.SLIDE_SPEED) {
            tile.setX(tile.getX() - distX);
        }

        if (Math.abs(distY) < Tile.SLIDE_SPEED) {
            tile.setY(tile.getY() - distY);
        }

        if (distX < 0) {
            tile.setX(tile.getX() + Tile.SLIDE_SPEED);
        }
        if (distY < 0) {
            tile.setY(tile.getY() + Tile.SLIDE_SPEED);
        }
        if (distX > 0) {
            tile.setX(tile.getX() - Tile.SLIDE_SPEED);
        }
        if (distY > 0) {
            tile.setY(tile.getY() - Tile.SLIDE_SPEED);
        }
    }


    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }


    public boolean isDead() {
        return dead;
    }

    public boolean isWon() {
        return won;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    //region Interface overrides

    /**
     * Gets the latest turn (= move of tile or creation of tile) to include it in the view
     */
    @Override
    public void refresh() {
        if (observable == null) return;

        int formerX = observable.giveFormerX();
        int formerY = observable.giveFormerY();
        int newX = observable.giveNewX();
        int newY = observable.giveNewY();
        int merged = observable.giveMergedValue();

        if(formerX == newX && formerY == newY) return;

        if (formerX == -1 && formerY == -1) { //new tile was created
            board[newX][newY] = new Tile(merged, newX, newY);
            return;
        }
        if (merged > -1) { //tile was merged
            board[newX][newY].setValue(merged);
            board[formerX][formerY] = null;
            board[newX][newY].setMergeAnimation(true);
        } else { //tile was not merged, just moved
            Tile tile = board[formerX][formerY];
            board[newX][newY] = tile;
            board[formerX][formerY] = null;
        }
    }

    @Override
    public void refreshGameCondition() {
        if(conditionObservable == null) return;

        if(conditionObservable.getGameCondition() == GameCondition.LOST){
            setDead(true);
        }else if(conditionObservable.getGameCondition() == GameCondition.WON){
            setWon(true);
        }
    }

    @Override
    public void setObservable(IGameConditionObservable matrix) { this.conditionObservable = matrix; }

    @Override
    public void setObservable(IBoardObservable observable) {
        this.observable = observable;
    }
    //endregion
}
