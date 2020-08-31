package game.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Represents a single tile on the GameBoard and provides functionality for animation
 *
 * @author Hauke Preisig
 * @version 0.1.0
 */
public class Tile {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int SLIDE_SPEED = 30;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;

    private final BufferedImage tileImage;
    private int x;
    private int y;
    private final BufferedImage spawnImage;
    private final BufferedImage mergeImage;
    private int value;
    private boolean spawnAnimation = true;
    private double spawnScale = 0.1;
    private boolean mergeAnimation = false;
    private double mergeScale = 1.2;

    //region Constructors
    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        spawnImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        mergeImage = new BufferedImage(WIDTH * 2, HEIGHT * 2, BufferedImage.TYPE_INT_ARGB);
        drawImage();
    }
    //endregion

    //region Public methods
    /**
     * Updates the animation (60 times a second) depending on the direction the tile moves (if it moves)
     * and if it merges with another tile
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void update() {
        if (spawnAnimation) {
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH / 2 - spawnScale * WIDTH / 2, HEIGHT / 2 - spawnScale * HEIGHT / 2);
            transform.scale(spawnScale, spawnScale);
            Graphics2D g2d = (Graphics2D) spawnImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            spawnScale += 0.1;
            g2d.dispose();
            if (spawnScale >= 1) spawnAnimation = false;
        } else if (mergeAnimation) {
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH / 2 - mergeScale * WIDTH / 2, HEIGHT / 2 - mergeScale * HEIGHT / 2);
            transform.scale(mergeScale, mergeScale);
            Graphics2D g2d = (Graphics2D) mergeImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            mergeScale -= 0.08;
            g2d.dispose();
            if (mergeScale <= 1) mergeAnimation = false;
        }
    }

    /**
     * Creates the tile in the graphic. Considers the current animation.
     *
     * @param g Graphics2D-Object, on which the tile should be painted
     */
    public void render(Graphics2D g) {
        if (spawnAnimation) {
            g.drawImage(spawnImage, x, y, null);
        } else if (mergeAnimation) {
            g.drawImage(mergeImage, (int) (x + WIDTH / 2 - mergeScale * WIDTH / 2),
                    (int) (y + HEIGHT / 2 - mergeScale * HEIGHT / 2), null);
        } else {
            g.drawImage(tileImage, x, y, null);
        }
    }
    //endregion

    //region Private methods
    /**
     * Draws the tile (color and number)
     */
    private void drawImage() {
        Graphics2D g = (Graphics2D) tileImage.getGraphics();
        Color backgroundColor;
        Color textColor;
        if (value == 2) {
            backgroundColor = new Color(0xe9e9e9);
            textColor = new Color(0x000000);
        } else if (value == 4) {
            backgroundColor = new Color(0xe6daab);
            textColor = new Color(0x000000);
        } else if (value == 8) {
            backgroundColor = new Color(0xf79d3d);
            textColor = new Color(0xffffff);
        } else if (value == 16) {
            backgroundColor = new Color(0xf28007);
            textColor = new Color(0xffffff);
        } else if (value == 32) {
            backgroundColor = new Color(0xf55e3b);
            textColor = new Color(0xffffff);
        } else if (value == 64) {
            backgroundColor = new Color(0xff0000);
            textColor = new Color(0xffffff);
        } else if (value == 128) {
            backgroundColor = new Color(0xe9de84);
            textColor = new Color(0xffffff);
        } else if (value == 256) {
            backgroundColor = new Color(0xf6e873);
            textColor = new Color(0xffffff);
        } else if (value == 512) {
            backgroundColor = new Color(0xf5e455);
            textColor = new Color(0xffffff);
        } else if (value == 1024) {
            backgroundColor = new Color(0xf7e12c);
            textColor = new Color(0xffffff);
        } else if (value == 2048) {
            backgroundColor = new Color(0xffe400);
            textColor = new Color(0xffffff);
        } else if(value == 0) {
            backgroundColor = Color.lightGray;
            textColor = Color.black;
        } else {
            backgroundColor = new Color(0x000000);
            textColor = new Color(0xffffff);
        }
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(backgroundColor);
        g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);

        g.setColor(textColor);

        Font pointFont;
        if (value <= 64)
            pointFont = DrawUtils.main.deriveFont(36f);
        else
            pointFont = DrawUtils.main;
        g.setFont(pointFont);

        int drawX = WIDTH / 2 - DrawUtils.getStringWidth("" + value, pointFont, g) / 2;
        int drawY = HEIGHT / 2 + DrawUtils.getStringHeight("" + value, pointFont, g) / 2;
        g.drawString("" + value, drawX, drawY);
        g.dispose();
    }
    //endregion

    //region Get/Set-Methods
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        drawImage();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMergeAnimation(boolean mergeAnimation) {
        this.mergeAnimation = mergeAnimation;
        if (mergeAnimation) mergeScale = 1.2;
    }

    //endregion
}
