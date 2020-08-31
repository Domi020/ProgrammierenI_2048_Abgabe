package game.model.leaderboard.test;

import game.model.leaderboard.Leaderboards;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaderboardsTest {
    private final String filePath = new File("").getAbsolutePath() + "/Scores";

    @Test
    public void createFile() {
        Leaderboards.getInstance().resetFile();
        Leaderboards.getInstance().init();
        Leaderboards.getInstance().addScore(2);
        Leaderboards.getInstance().addScore(3);
        Leaderboards.getInstance().addScore(4);

        Leaderboards.getInstance().saveScores();

        File toCheck = new File(filePath);
        assertTrue(toCheck.exists());
    }

    @Test
    public void saveCorrectHighScores() {
        Leaderboards.getInstance().resetFile();
        Leaderboards.getInstance().init();
        Leaderboards.getInstance().addScore(2);
        Leaderboards.getInstance().addScore(3);
        Leaderboards.getInstance().addScore(4);

        ArrayList<Integer> scores = Leaderboards.getInstance().getHighscores();
        assertEquals((int) scores.get(0), 4);
        assertEquals((int) scores.get(1), 3);
        assertEquals((int) scores.get(2), 2);


    }
}
