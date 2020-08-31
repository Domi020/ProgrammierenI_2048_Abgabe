package game.view;

import game.controller.GameManager;
import game.controller.listeners.GameKeyListener;
import game.controller.listeners.MouseListener;
import game.controller.listeners.MouseMotionListener;
import game.view.GUI.AIPanel;
import game.view.GUI.LeaderboardsPanel;
import game.view.GUI.MainMenuPanel;
import game.view.GUI.PlayPanel;
import game.view.intf.GuiPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Sets up the different parts of the view and manages updates and refreshes
 * for all view objects (where needed). Uses Singleton as only one ViewManager should exist.
 *
 * @author Hauke Preisig
 * @version 0.5.0
 */
public class ViewManager extends JPanel {

    private static ViewManager viewManager;
    private final BufferedImage image = new BufferedImage(DrawUtils.WIDTH, DrawUtils.HEIGHT, BufferedImage.TYPE_INT_RGB);

    private final HashMap<String, GuiPanel> panels = new HashMap<>();
    private String currentPanel = "";
    private PlayPanel playPanel;
    private LeaderboardsPanel leaderboardsPanel;
    private Grid board;

    private ViewManager() {
    }

    public static ViewManager getInstance() {
        if (viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    /**
     * Initializes different parts of the view like the panels or the RunController
     *
     * @param board The grid on which the view is based
     */
    public void init(Grid board) {
        RunController runController = new RunController(this);

        setFocusable(true);
        setPreferredSize(new Dimension(DrawUtils.WIDTH, DrawUtils.HEIGHT));

        panels.put("Menu", new MainMenuPanel());
        PlayPanel panel = new PlayPanel(board);
        panels.put("Play", panel);
        panel.setObservable(GameManager.getScoreObservable());
        playPanel = panel;
        leaderboardsPanel = new LeaderboardsPanel();
        panels.put("Leaderboards", leaderboardsPanel);
        panels.put("AI", new AIPanel());
        setCurrentPanel("Menu");

        //add listeners for GUI
        addMouseMotionListener(new MouseMotionListener(this));
        addMouseListener(new MouseListener(this));
        addKeyListener(new GameKeyListener());

        this.board = board;
        runController.start();
    }

    /**
     * Begins drawing the view. Calls the current panel, to draw its part of the view
     */
    public void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, DrawUtils.WIDTH, DrawUtils.HEIGHT);
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).render(g);
        }
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    /**
     * Calls update methods of grid and PlayPanel to make animations
     */
    public void update() {
        board.update();
        playPanel.update();
    }

    public PlayPanel getPlayPanel() {
        return playPanel;
    }

    public void add(String panelName, GuiPanel panel) {
        panels.put(panelName, panel);
    }

    public LeaderboardsPanel getLeaderboardsPanel() {
        return leaderboardsPanel;
    }

    public void setCurrentPanel(String panelName) {
        currentPanel = panelName;
    }

    public void mousePressed(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseMoved(e);
        }
    }
}

