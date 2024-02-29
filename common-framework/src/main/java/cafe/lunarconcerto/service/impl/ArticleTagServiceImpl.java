package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.mapper.ArticleTagMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.domain.entity.ArticleTag;
import cafe.lunarconcerto.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-30 12:44:17
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public List<ArticleTag> listByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        return list(queryWrapper);
    }
}

