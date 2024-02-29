package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.Comment;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LunarConcerto
 * @time 2023/12/24
 */
@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult<?> commentList(Long articleId,Integer pageNum, Integer pageSize){
        return commentService.commentList(articleId, pageNum, pageSize, SystemConstants.ARTICLE_COMMENT);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult<?> linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList(null, pageNum, pageSize, SystemConstants.LINK_COMMENT);
    }

    @PostMapping
    public ResponseResult<?> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

}
