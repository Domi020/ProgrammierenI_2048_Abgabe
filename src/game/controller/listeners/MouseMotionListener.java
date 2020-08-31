package game.controller.listeners;

import game.view.ViewManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotionListener extends MouseMotionAdapter {
    private final ViewManager viewManager;

    public MouseMotionListener(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        viewManager.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        viewManager.mouseMoved(e);
    }
}
