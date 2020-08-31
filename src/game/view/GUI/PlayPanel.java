package game.view.GUI;

import game.controller.GameManager;
import game.model.intf.IScoreObservable;
import game.view.DrawUtils;
import game.view.Grid;
import game.view.ViewManager;
import game.view.intf.GuiPanel;
import game.view.intf.IButton;
import game.view.intf.IScoreObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayPanel implements IScoreObserver, GuiPanel {

    private final ArrayList<IButton> buttons = new ArrayList<>();

    private final Grid board;
    private final BufferedImage info;
    private final Font scoreFont;

    private IScoreObservable observable;
    private int currentScore = 0;
    private int highScore = 0;

    private final IButton tryAgain;
    private final IButton mainMenu;
    private final IButton restart;
    private final IButton save;

    private boolean added;
    private boolean restartAdded;
    private final Font gameOverFont;
    private int blankingOut = 0;
    private boolean justSaved = false;
    private int justSavedCounter = 0;

    public PlayPanel(Grid board) {
        scoreFont = DrawUtils.main.deriveFont(24f);
        gameOverFont = DrawUtils.main.deriveFont(70f);
        this.board = board;
        info = new BufferedImage(DrawUtils.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

        int spacing = 20;
        int smallButtonWidth = 160;
        int largeButtonWidth = smallButtonWidth * 2 + spacing;
        int buttonHeight = 50;
        mainMenu = new Button(DrawUtils.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
        tryAgain = new Button(DrawUtils.WIDTH / 2 - largeButtonWidth / 2, 350, largeButtonWidth, buttonHeight);
        restart = new Button(300, 100, smallButtonWidth, buttonHeight);
        save = new Button(100, 100, smallButtonWidth, buttonHeight);

        observable = GameManager.getScoreObservable();
        tryAgain.setText("Try Again");
        mainMenu.setText("Back to Main Menu");
        restart.setText("Restart Game");
        save.setText("Save Game");

        tryAgain.addActionListener(e -> {
            board.reset();
            blankingOut = 0;
            remove(tryAgain);
            remove(mainMenu);
            ViewManager.getInstance().setCurrentPanel("AI");
            restartAdded = false;
            added = false;
        });

        mainMenu.addActionListener(e -> {
            ViewManager.getInstance().setCurrentPanel("Menu");
            remove(tryAgain);
            remove(mainMenu);
            restartAdded = false;
        });
        restart.addActionListener(e -> {
            board.reset();
            blankingOut = 0;
            remove(tryAgain);
            remove(mainMenu);
            ViewManager.getInstance().setCurrentPanel("AI");
            added = false;
        });
        save.addActionListener(e -> {
            GameManager.saveGame();
            justSaved = true;
        });
    }

    private void drawGui(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) info.getGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
        g2d.setColor(Color.lightGray);
        g2d.setFont(scoreFont);
        g2d.drawString("" + currentScore, 30, 40);
        g2d.setColor(Color.red);
        g2d.drawString("Best: " + highScore, DrawUtils.WIDTH - DrawUtils.getStringWidth("Best: " + highScore, scoreFont, g2d) - 20, 40);
        g2d.dispose();
        g.drawImage(info, 0, 0, null);
    }

    public void drawGameOver(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, blankingOut));
        g.fillRect(0, 0, DrawUtils.WIDTH, DrawUtils.HEIGHT);
        g.setColor(Color.red);
        g.drawString("Game Over!", DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth("Game Over!", gameOverFont, g) / 2, 250);
    }

    public void drawWin(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, blankingOut));
        g.fillRect(0, 0, DrawUtils.WIDTH, DrawUtils.HEIGHT);
        g.setColor(Color.green);
        g.drawString("YOU WIN!", DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth("Game Over!", gameOverFont, g) / 2, 250);
    }

    public void update() {
        board.update();
        if (board.isDead()) {
            blankingOut++; //raise brightness when dead
            if (blankingOut > 170) blankingOut = 170;
        }
    }

    @Override
    public void render(Graphics2D g) {
        drawGui(g);
        board.render(g);
        if (!restartAdded) {
            add(restart);
            add(save);
            restartAdded = true;
        }
        if (board.isDead()) {
            if (!added) {
                added = true;
                remove(restart);
                remove(save);
                add(mainMenu);
                add(tryAgain);
            }
            drawGameOver(g);
        }
        if (board.isWon()) {
            if (!added) {
                added = true;
                remove(restart);
                remove(save);
                add(mainMenu);
                add(tryAgain);
            }
            drawWin(g);
        }
        if (justSaved) {
            g.setColor(Color.orange);
            g.drawString("saved", DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth("saved", gameOverFont, g) / 2, 70);
            justSavedCounter++;
        }
        if (justSavedCounter > 60) {
            justSaved = false;
            justSavedCounter = 0;
        }
        for (IButton b : buttons) {
            b.render(g);
        }
    }

    @Override
    public void add(IButton button) {
        buttons.add(button);
    }

    @Override
    public void remove(IButton button) {
        buttons.remove(button);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).mouseReleased(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).mouseMoved(e);
        }
    }

    @Override
    public void refreshScore() {
        if (observable != null) currentScore = observable.getCurrentScore();
        if (observable != null) highScore = observable.getHighScore();
    }

    @Override
    public void setObservable(IScoreObservable observable) {
        this.observable = observable;
    }
}
