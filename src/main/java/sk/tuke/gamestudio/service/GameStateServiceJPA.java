package sk.tuke.gamestudio.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.GameState;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameStateServiceJPA implements GameStateService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(GameState state) throws GameStateException {
        entityManager.persist(state);
    }

    @Override
    public GameState load(String gameName) throws GameStateException {
        List<GameState> states = entityManager.createNamedQuery("GameState.load")
                .setParameter("gameName", gameName)
                .getResultList();

        if (!states.isEmpty()) {
            return states.get(0);
        }
        return null;
    }
}
