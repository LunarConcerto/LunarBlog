package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.Comment;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.CommentVo;
import cafe.lunarconcerto.domain.vo.PageVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.mapper.CommentMapper;
import cafe.lunarconcerto.service.CommentService;
import cafe.lunarconcerto.service.UserService;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-24 22:49:09
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private static final Long ROOT_ID = -1L;

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult<?> commentList(Long articleId, Integer pageNum, Integer pageSize, String type) {
        // 查询对应文章的所有根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        // 对articleId进行判断
        if (SystemConstants.ARTICLE_COMMENT.equals(type)){
            queryWrapper.eq(Comment::getArticleId, articleId);
        }

        // 是根评论, 也就是rootId = -1
        queryWrapper.eq(Comment::getRootId, ROOT_ID);

        // 文章类型
        queryWrapper.eq(Comment::getType, type);

        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        // 查询所有根评论对应的子评论集合, 并且赋值给对应的属性.
        for (CommentVo vo : commentVoList) {
            // 查询对应的子评论 并进行赋值
            List<CommentVo> children = getChildren(vo.getId());

            vo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult<?> addComment(@NotNull Comment comment) {
        // 评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_IS_NULL);
        }

        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 获取某个评论的所有子评论
     * @param id 根评论ID
     * @return 子评论集合
     */
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = list(queryWrapper);

        return toCommentVoList(comments);
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        // 遍历
        for (CommentVo vo : voList) {
            // 通过createBy查询用户的昵称并赋值
            vo.setUsername(userService.getById(vo.getCreateBy()).getNickName());

            // 通过toCommentUserId查询用户的昵称并赋值

            // 判断是否为-1
            if (vo.getToCommentUserId() == -1){
                continue;
            }

            vo.setToCommentUserName(userService.getById(vo.getToCommentUserId()).getNickName());
        }

        return voList;
    }

}

