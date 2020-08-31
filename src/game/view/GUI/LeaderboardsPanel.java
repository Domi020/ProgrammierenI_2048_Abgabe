package game.view.GUI;

import game.model.intf.ILeaderboardObservable;
import game.view.DrawUtils;
import game.view.ViewManager;
import game.view.intf.GuiPanel;
import game.view.intf.IButton;
import game.view.intf.ILeaderboardObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The leaderboard panel, on which the scores and tiles highscores are shown
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 0.5.0
 */
public class LeaderboardsPanel implements ILeaderboardObserver, GuiPanel {

    private final ArrayList<IButton> buttons = new ArrayList<>();

    private ILeaderboardObservable observable;
    private final int buttonY = 120;
    private ArrayList<Integer> tiles;
    private final int buttonHeight = 50;
    private final Font titleFont = DrawUtils.main.deriveFont(48f);
    private final Font scoreFont = DrawUtils.main.deriveFont(30f);
    private ArrayList<Integer> highScores;
    private State currentState = State.SCORE;

    public LeaderboardsPanel() {
        buildButtons();
    }

    /**
     * Builds the buttons on the leaderboard panel
     */
    private void buildButtons() {
        int buttonWidth = 100;
        IButton tileButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2 + buttonWidth, buttonY, buttonWidth, buttonHeight);
        tileButton.addActionListener(e -> currentState = State.TILE);
        tileButton.setText("Tiles");
        add(tileButton);

        int buttonSpacing = 20;
        IButton scoreButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2 - tileButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
        scoreButton.addActionListener(e -> currentState = State.SCORE);
        scoreButton.setText("Scores");
        add(scoreButton);

        int backButtonWidth = 220;
        IButton backButton = new Button(DrawUtils.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
        backButton.addActionListener(e -> ViewManager.getInstance().setCurrentPanel("Menu"));
        backButton.setText("Back");
        add(backButton);
    }

    /**
     * Draws the leaderboard
     * @param g The Graphics2D object, on which should be drawn
     */
    private void drawLeaderboards(Graphics2D g) {
        ArrayList<String> strings = new ArrayList<>();
        if (currentState == State.SCORE) {
            if (highScores != null) strings = convertToStrings(highScores);
        } else if (currentState == State.TILE) {
            if (tiles != null) strings = convertToStrings(tiles);
        }

        g.setColor(Color.black);
        g.setFont(scoreFont);

        for(int i = 0; i < strings.size(); i++) {
            String s = (i + 1) + ". " + strings.get(i);
            int leaderboardsX = 130;
            int leaderboardsY = buttonY + buttonHeight + 90;
            g.drawString(s, leaderboardsX, leaderboardsY + i * 40);
        }
    }

    private ArrayList<String> convertToStrings(ArrayList<? extends Number> list) {
        ArrayList<String> ret = new ArrayList<>();
        for (Number n : list) {
            ret.add(n.toString());
        }
        return ret;
    }


    //region GuiPanel override

    /**
     * Draws the panel
     *
     * @param g The Graphics2D object, on which should be drawn
     */
    @Override
    public void render(Graphics2D g) {
        for (IButton b : buttons) {
            b.render(g);
        }
        g.setColor(Color.black);
        String title = "Leaderboards";
        g.drawString(title, DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth(title, titleFont, g) / 2, DrawUtils.getStringHeight(title, titleFont, g) + 40);
        drawLeaderboards(g);
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

    //endregion


    //region ILeaderboardObserver overrides

    @Override
    public void refreshScore() {
        if (observable != null) highScores = observable.getHighscores();
        if (observable != null) tiles = observable.getTiles();
    }

    @Override
    public void setObservable(ILeaderboardObservable observable) {
        this.observable = observable;
    }

    //endregion

    private enum State {
        SCORE,
        TILE
    }
}
