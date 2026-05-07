package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.AdditionalComment;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.persistence.EntityManager;

@Service
@Transactional
public class AdditionalCommentServiceJPA implements AdditionalCommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addAdditionalComment(AdditionalComment additionalComment) throws AdditionalCommentException {
        if (additionalComment.getParent() != null && additionalComment.getParent().getId() != 0) {
            AdditionalComment parent = entityManager.find(AdditionalComment.class, additionalComment.getParent().getId());
            additionalComment.setParent(parent);
        }
        entityManager.persist(additionalComment);
    }

    @Override
    public List<AdditionalComment> getAdditionalComments(String comment) throws AdditionalCommentException {
        return entityManager.createNamedQuery("AdditionalComment.getAdditionalComment")
                .setParameter("comment", comment).getResultList();
    }

    @Override
    public void reset() throws AdditionalCommentException {
        entityManager.createNamedQuery("AdditionalComment.resetAdditionalComment").executeUpdate();
    }
}
