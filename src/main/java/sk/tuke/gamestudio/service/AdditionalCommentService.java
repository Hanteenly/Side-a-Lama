package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.AdditionalComment;
import sk.tuke.gamestudio.entity.AdditionalComment;

import java.util.List;

public interface AdditionalCommentService {
    void addAdditionalComment(AdditionalComment additionalComment) throws AdditionalCommentException;
    List<AdditionalComment> getAdditionalComments(String comment);
    void reset() throws AdditionalCommentException;
}
