package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.service.ArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

/*
    @GetMapping("/list")
    public String test(){
        return articleService.list().toString();
    }
*/

    /**
     * 查询热门文章
     * @return 封装为 ResponseResult
     */
    @GetMapping("/hotArticleList")
    public ResponseResult<?> hotArticleList(){
        return articleService.hotArticleList();
    }

    /**
     * 查询文章列表
     * @return 封装为 ResponseResult
     */
    @GetMapping("/articleList")
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryID){
        return articleService.articleList(pageNum, pageSize, categoryID);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult<?> updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}
