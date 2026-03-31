package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;


import java.util.Date;


class CommentServiceTest {
    /*
    private CommentService service = new CommentServiceJDBC();

    @Test
    void testAddAndGetComment() throws CommentException {
        service.reset();
        Comment c = new Comment("side-a-lama", "Ivan", "Great game!", new Date());
        service.addComment(c);

        var comments = service.getComments("side-a-lama");

        assertEquals(1, comments.size());
        assertEquals("Ivan", comments.get(0).getPlayer());
        assertEquals("Great game!", comments.get(0).getComments());
    }

    @Test
    void testReset() throws CommentException {
        service.addComment(new Comment("side-a-lama", "P1", "Nice", new Date()));
        service.reset();
        assertEquals(0, service.getComments("side-a-lama").size());
    }
     */
}
