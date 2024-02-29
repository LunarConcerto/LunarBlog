package cafe.lunarconcerto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cafe.lunarconcerto.domain.entity.ArticleTag;

import java.util.List;

/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2023-12-30 12:44:17
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<ArticleTag> listByArticleId(Long id);

}

