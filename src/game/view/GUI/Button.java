package game.view.GUI;

import game.view.DrawUtils;
import game.view.intf.IButton;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Represents a GUI button
 *
 * @author Hauke Preisig
 * @version 0.5.0
 */
public class Button implements IButton {
    private final Rectangle ButtonBox; //AWT rectangle, which represents the box
    private final ArrayList<ActionListener> actionListeners = new ArrayList<>();
    private final Color main = new Color(173, 177, 179);
    private final Color hover = new Color(150, 156, 158);
    private final Color pressed = new Color(111, 116, 117);
    private final Font font = DrawUtils.main.deriveFont(22f);
    private State currentState = State.RELEASED;
    private String text = "";

    public Button(int x, int y, int width, int height) {
        ButtonBox = new Rectangle(x, y, width, height);
    }

    /**
     * Paints the button
     *
     * @param g The Graphics2D object on which should be painted
     */
    @Override
    public void render(Graphics2D g) {
        if (currentState == State.RELEASED) {
            g.setColor(main);
        } else if (currentState == State.PRESSED) {
            g.setColor(pressed);
        } else {
            g.setColor(hover);
        }
        g.fill(ButtonBox);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(text, ButtonBox.x + ButtonBox.width / 2 - DrawUtils.getStringWidth(text, font, g) / 2, ButtonBox.y + ButtonBox.height / 2 + DrawUtils.getStringHeight(text, font, g) / 2);
    }

    @Override
    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (ButtonBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (ButtonBox.contains(e.getPoint())) {
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(null);
            }
        }
        currentState = State.RELEASED;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (ButtonBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        } else {
            currentState = State.RELEASED;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (ButtonBox.contains(e.getPoint())) {
            currentState = State.HOVER;
        } else {
            currentState = State.RELEASED;
        }
    }

    @Override
    public int getX() {
        return ButtonBox.x;
    }

    @Override
    public int getY() {
        return ButtonBox.y;
    }

    @Override
    public int getWidth() {
        return ButtonBox.width;
    }

    @Override
    public int getHeight() {
        return ButtonBox.height;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    private enum State {
        HOVER, RELEASED, PRESSED
    }
}
