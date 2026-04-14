package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public List<Rating> getAllRatings() throws RatingException {
        return entityManager.createNamedQuery("Rating.getAllRatings").getResultList();
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double avg = (Double) entityManager.createNamedQuery("Rating.getAverageRating")
                .setParameter("game", game)
                .getSingleResult();

        if(avg != null){
            return avg.intValue();
        }else {
            return 0;
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        List<Rating> ratings = entityManager.createNamedQuery("Rating.getRating")
                .setParameter("game", game)
                .setParameter("player", player)
                .getResultList();

        if (!ratings.isEmpty()) {
            return ratings.get(0).getRating();
        }
        return 0;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}
