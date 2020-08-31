package game.view.intf;


import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Interface for all panels
 *
 * @author Dominik Sauerer, Hauke Preisig
 * @version 1.0.0
 */
public interface GuiPanel {

    void render(Graphics2D g);

    void add(IButton button);

    void remove(IButton button);

    void mousePressed(MouseEvent e);

    void mouseReleased(MouseEvent e);

    void mouseDragged(MouseEvent e);

    void mouseMoved(MouseEvent e);
}
