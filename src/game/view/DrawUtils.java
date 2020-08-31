package game.view;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 * Help methods for drawing particular strings
 *
 * @author Hauke Preisig
 * @version 0.1.0
 */
public class DrawUtils {

    public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
    public static final int WIDTH = Grid.BOARD_WIDTH + 40;
    public static final int HEIGHT = 630;

    /**
     * Calculates the need width to display a string in a Graphics2D object
     *
     * @param string the to be displayed string
     * @param font   the font, in which the string is displayed
     * @param g      the Graphics2D object, in which the string should be painted
     * @return the need width
     */
    public static int getStringWidth(String string, Font font, Graphics2D g) {
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(string, g);
        return (int) bounds.getWidth();
    }

    /**
     * Calculates the need height to display a string in a Graphics2D object
     *
     * @param string the to be displayed string
     * @param font   the font, in which the string is displayed
     * @param g      the Graphics2D object, in which the string should be painted
     * @return the need height
     */
    public static int getStringHeight(String string, Font font, Graphics2D g) {
        g.setFont(font);
        if (string.length() == 0) return 0;
        TextLayout tl = new TextLayout(string, font, g.getFontRenderContext());
        return (int) tl.getBounds().getHeight();
    }
}
