package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.Article;
import cafe.lunarconcerto.service.ArticleService;
import cafe.lunarconcerto.domain.entity.Category;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.CategoryVo;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.mapper.CategoryMapper;
import cafe.lunarconcerto.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-04-26 11:02:00
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService ;

    @Override
    public ResponseResult<List<CategoryVo>> getCategoryList() {
        // 查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<Article> articles = articleService.list(queryWrapper);
        // 获取文章的分类ID 并且去重
        Set<Long> categoryIdSet = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 查询分类表
        List<Category> categoryList = listByIds(categoryIdSet)
                .stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .toList();

        // 封装 VO
        List<CategoryVo> beanList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        return ResponseResult.okResult(beanList);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        return BeanCopyUtils.copyBeanList(list, CategoryVo.class);
    }


}

