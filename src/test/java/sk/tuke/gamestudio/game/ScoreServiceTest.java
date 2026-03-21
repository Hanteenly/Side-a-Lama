package sk.tuke.gamestudio.game;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ScoreServiceTest {
    private ScoreService service = new ScoreServiceJDBC();

    @Test
    void testAddAndGetScore() throws ScoreException {
        service.reset();
        Score s = new Score("side-a-lama", "Player1", 500, new Date());
        service.addScore(s);

        var scores = service.getTopScores("side-a-lama");
        assertEquals(1, scores.size());
        assertEquals(500, scores.get(0).getPoints());
    }

    @Test
    void testReset() throws ScoreException {
        service.addScore(new Score("side-a-lama", "P1", 100, new Date()));
        service.reset();
        assertEquals(0, service.getTopScores("side-a-lama").size());
    }
}
