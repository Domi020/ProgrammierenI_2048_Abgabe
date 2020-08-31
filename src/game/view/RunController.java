package game.view;

/**
 * Creates a thread, which refreshes the game about 60 times per second. Necessary for fluent animations
 * (which consist of single pictures).
 *
 * @author Hauke Preisig, Dominik Sauerer
 * @version 0.5.0
 */
public class RunController implements Runnable {

    private boolean running;
    private static RunController instance;
    private final ViewManager viewManager;

    //region Constructors
    public RunController(ViewManager viewManager) {
        this.viewManager = viewManager;
        instance = this;
    }
    //endregion

    public static RunController getInstance() {
        return instance;
    }

    //region Get/Set-Methods
    public ViewManager getViewManager() {
        return viewManager;
    }
    //endregion

    //region Public methods
    /**
     * Starts a new thread
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        Thread gameController = new Thread(this, "game");
        gameController.start();
    }

    /**
     * Run-Method of RunController thread, which uses System timer methods, to count
     * 60 refreshes per second
     */
    @Override
    public void run() {
        int fps = 0, update = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        double lastRun = System.nanoTime();
        double sinceLastUpdate = 0;

        while (running) {
            boolean shouldRender = false;
            double timeNow = System.nanoTime();
            sinceLastUpdate += (timeNow - lastRun) / nsPerUpdate; //time in percent since last refresh/update
            lastRun = timeNow;
            while (sinceLastUpdate >= 1) {
                update++;
                viewManager.update();
                sinceLastUpdate--;
                shouldRender = true;
            }
            if (shouldRender) {
                fps++;
                viewManager.render();
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (System.currentTimeMillis() - fpsTimer > 1000) {
                System.out.printf("%d fps %d updates", fps, update);
                System.out.println();
                fps = 0;
                update = 0;
                fpsTimer += 1000;
            }
        }
    }
    //endregion
}
