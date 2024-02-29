package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.dto.AddArticleDto;
import cafe.lunarconcerto.domain.dto.UpdateArticleDto;
import cafe.lunarconcerto.domain.entity.Article;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.dto.PageArticleDto;
import cafe.lunarconcerto.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {

    ResponseResult<?> hotArticleList();

    ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryID);

    ResponseResult<?> getArticleDetail(Long id);

    ResponseResult<?> updateViewCount(Long id);

    ResponseResult<?> add(AddArticleDto article);

    ResponseResult<PageVo> pageArticle(Integer pageNum, Integer pageSize, PageArticleDto pageArticleDto);

    ResponseResult<?> getArticle(String id);

    ResponseResult<?> updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult<?> removeArticle(Long id);
}
