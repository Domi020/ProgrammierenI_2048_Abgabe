package game.view.GUI;

import game.controller.GameManager;
import game.view.DrawUtils;
import game.view.ViewManager;
import game.view.intf.GuiPanel;
import game.view.intf.IButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The main menu, which appears, when the game is started
 *
 * @author Hauke Preisig
 * @version 1.0.0
 */
public class MainMenuPanel implements GuiPanel {

    private final ArrayList<IButton> buttons = new ArrayList<>();

    private final Font titleFont = DrawUtils.main.deriveFont(100f);
    private final Font creatorFont = DrawUtils.main.deriveFont(24f);

    public MainMenuPanel() {
        buildButtons();
    }

    /**
     * Builds the buttons on the leaderboard panel
     */
    private void buildButtons() {
        int buttonWidth = 220;
        IButton playButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, 60);
        playButton.addActionListener(e -> ViewManager.getInstance().setCurrentPanel("AI"));
        playButton.setText("New Game");
        add(playButton);

        IButton loadButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2, 310, buttonWidth, 60);
        loadButton.addActionListener(e -> {
            ViewManager.getInstance().setCurrentPanel("Play");
            GameManager.loadGame();
        });
        loadButton.setText("Load Game");
        add(loadButton);

        IButton scoresButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2, 400, buttonWidth, 60);
        scoresButton.addActionListener(e -> ViewManager.getInstance().setCurrentPanel("Leaderboards"));
        scoresButton.setText("Scores");
        add(scoresButton);

        IButton quitButton = new Button(DrawUtils.WIDTH / 2 - buttonWidth / 2, 490, buttonWidth, 60);
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setText("Quit");
        add(quitButton);
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
        g.setFont(titleFont);
        g.setColor(Color.black);
        String title = "2048";
        g.drawString(title, DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth(title, titleFont, g) / 2, 150);
        g.setFont(creatorFont);
        String creator = "Version 1.0.0";
        g.drawString(creator, 20, DrawUtils.HEIGHT - 10);
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
}
