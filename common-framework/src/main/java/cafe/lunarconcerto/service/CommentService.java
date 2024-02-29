package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.Comment;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-12-24 22:49:08
 */
public interface CommentService extends IService<Comment> {

    ResponseResult<?> commentList(Long articleId, Integer pageNum, Integer pageSize, String type);


    ResponseResult<?> addComment(Comment comment);
}

