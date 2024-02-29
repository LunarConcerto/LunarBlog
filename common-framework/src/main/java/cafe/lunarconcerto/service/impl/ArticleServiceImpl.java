package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.dto.AddArticleDto;
import cafe.lunarconcerto.domain.dto.PageArticleDto;
import cafe.lunarconcerto.domain.dto.UpdateArticleDto;
import cafe.lunarconcerto.domain.entity.Article;
import cafe.lunarconcerto.domain.entity.ArticleTag;
import cafe.lunarconcerto.domain.entity.Category;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.*;
import cafe.lunarconcerto.mapper.ArticleMapper;
import cafe.lunarconcerto.service.ArticleService;
import cafe.lunarconcerto.service.ArticleTagService;
import cafe.lunarconcerto.service.CategoryService;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import cafe.lunarconcerto.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;


    /**
     * 查询热门文章
     * <p>
     * 	需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。
     * 	把能让用户自己点击跳转到具体的文章详情进行浏览。
     * <p>
     *  注意：不能把草稿展示出来，不能把删除了的文章查询出来。
     *  要按照浏览量进行降序排序。
     */
    @Override
    public ResponseResult<?> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多10条(分页)
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

//        // bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>(articles.size());
//
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArticleVo.class));
    }

    @Override
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryID) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 如果有categoryID 就要 查询时和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryID) && categoryID > 0,
                Article::getCategoryId, categoryID);

        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        // 对 isTop进行降序排序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        // 查询CategoryName
        List<Article> records = page.getRecords();

        List<CategoryVo> categoryVoList = categoryService.getCategoryList().getData();
        Map<Long, String> categoryID2Name = categoryVoList.stream().collect(Collectors.toMap(CategoryVo::getId, CategoryVo::getName, (a, b) -> b));
        records.forEach(article -> article.setCategoryName(categoryID2Name.get(article.getId())));

        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<?> getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);

        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_REDIS_KEY, id.toString());
        article.setViewCount(viewCount.longValue());

        // 转换为vo
        ArticleDetailVo detailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        // 根据分类id 查询分类名
        Long categoryId = detailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            detailVo.setCategoryName(category.getName());
        }

        // 封装响应返回
        return ResponseResult.okResult(detailVo);
    }

    @Override
    public ResponseResult<?> updateViewCount(@NotNull Long id) {
        // 更新 redis 中
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT_REDIS_KEY, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(Objects.requireNonNull(article).getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageArticle(Integer pageNum, Integer pageSize, @NotNull PageArticleDto pageArticleDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(pageArticleDto.getTitle()), Article::getTitle, pageArticleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(pageArticleDto.getSummary()), Article::getSummary, pageArticleDto.getSummary());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<?> getArticle(String id) {
        Article article = getById(id);

        GetArticleVo getArticleVo = BeanCopyUtils.copyBean(article, GetArticleVo.class);


        List<ArticleTag> tagList = articleTagService.listByArticleId(article.getId());

        Objects.requireNonNull(getArticleVo).setTags(tagList.stream().map(articleTag -> articleTag.getTagId().toString()).toList());

        return ResponseResult.okResult(getArticleVo);
    }

    @Override
    public ResponseResult<?> updateArticle(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);

        articleTagService.removeBatchByIds(articleTagService.listByArticleId(updateArticleDto.getId()).stream().map(ArticleTag::getTagId).toList());


        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(Objects.requireNonNull(article).getId(), Long.parseLong(tagId)))
                .toList();

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> removeArticle(Long id) {
        Article article = new Article();

        article.setId(id);
        article.setDelFlag(SystemConstants.STATUS_DELETE);

        updateById(article);

        return ResponseResult.okResult();
    }

}
