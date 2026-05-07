package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.AdditionalComment;
import sk.tuke.gamestudio.service.AdditionalCommentService;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;


@RestController
@RequestMapping("/api/additionalComment")
public class AdditionalCommentServiceRest {
    @Autowired
    private AdditionalCommentService additionalCommentService;

    @GetMapping("/{comment}")
    public List<AdditionalComment> getAdditionalComment(@PathVariable String comment) {
        return additionalCommentService.getAdditionalComments(comment);
    }

    @PostMapping
    public void AddAdditionalCommentService(@RequestBody AdditionalComment AdditionalComment){
        additionalCommentService.addAdditionalComment(AdditionalComment);
    }
}
