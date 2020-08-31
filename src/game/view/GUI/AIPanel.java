package game.view.GUI;

import game.AIType;
import game.controller.GameManager;
import game.model.AI.AIFactory;
import game.view.DrawUtils;
import game.view.ViewManager;
import game.view.intf.GuiPanel;
import game.view.intf.IButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Panel for AI selection at the start of the game
 *
 * @author Dominik Sauerer
 * @version 0.1.0
 */
public class AIPanel implements GuiPanel {

    private final ArrayList<IButton> buttons = new ArrayList<>();

    public AIPanel() {
        buildButtons();
    }

    /**
     * Builds the AI selection by creating and placing the buttons
     */
    private void buildButtons() {
        IButton random = new Button(180, 200, 150, 50);
        IButton cooperative = new Button(180, 300, 150, 50);
        IButton minMax = new Button(180, 400, 150, 50);
        IButton minMaxMulti = new Button(130, 500, 250, 50);

        random.setText("Random");
        cooperative.setText("Cooperative");
        minMax.setText("Minimax");
        minMaxMulti.setText("Minimax Multithreading");

        random.addActionListener(e -> {
            AIFactory.setCurrentAI(AIType.RANDOM);
            setUpGame();
        });
        cooperative.addActionListener(e -> {
            AIFactory.setCurrentAI(AIType.COOPERATIVE);
            setUpGame();
        });
        minMax.addActionListener(e -> {
            AIFactory.setCurrentAI(AIType.MINMAX);
            setUpGame();
        });
        minMaxMulti.addActionListener(e -> {
            AIFactory.setCurrentAI(AIType.MINMAXMULTI);
            setUpGame();
        });

        buttons.add(random);
        buttons.add(cooperative);
        buttons.add(minMax);
        buttons.add(minMaxMulti);
    }

    private void setUpGame() {
        ViewManager.getInstance().setCurrentPanel("Play");
        GameManager.newGame();
    }

    //region Override GuiPanel

    /**
     * Render method for AI panel, which draws the writing and the buttons
     *
     * @param g Graphics2D object, on which should be drawn
     */
    @Override
    public void render(Graphics2D g) {
        for (IButton b : buttons) {
            b.render(g);
        }
        g.setFont(DrawUtils.main.deriveFont(40f));
        g.setColor(Color.red);
        g.drawString("Select AI", DrawUtils.WIDTH / 2 - DrawUtils.getStringWidth("Select AI", DrawUtils.main.deriveFont(80f), g) / 2, 130);
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
