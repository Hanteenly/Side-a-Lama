package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.ScoreService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("/api/сomment")
public class CommentServiceRest {
    @Autowired
    private CommentService сommentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return сommentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        сommentService.addComment(comment);
    }

}
