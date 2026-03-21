package sk.tuke.gamestudio.game;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class RatingServiceTest {
    private RatingService service = new RatingServiceJDBC();

    @Test
    void testRating() throws RatingException {
        service.reset();
        service.setRating(new Rating("side-a-lama", "P1", 5, new Date()));
        service.setRating(new Rating("side-a-lama", "P2", 1, new Date()));

        assertEquals(3, service.getAverageRating("side-a-lama"));
    }
}
