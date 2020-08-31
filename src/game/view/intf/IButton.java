package game.view.intf;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public interface IButton {
    void render(Graphics2D g);

    void addActionListener(ActionListener listener);

    void mousePressed(MouseEvent e);

    void mouseReleased(MouseEvent e);

    void mouseDragged(MouseEvent e);

    void mouseMoved(MouseEvent e);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    void setText(String text);
}
