package game.controller.listeners;

import game.view.ViewManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    private final ViewManager viewManager;

    public MouseListener(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        viewManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        viewManager.mouseReleased(e);
    }
}
