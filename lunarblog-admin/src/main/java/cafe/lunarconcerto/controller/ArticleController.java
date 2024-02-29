package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.dto.AddArticleDto;
import cafe.lunarconcerto.domain.dto.UpdateArticleDto;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.dto.PageArticleDto;
import cafe.lunarconcerto.domain.vo.PageVo;
import cafe.lunarconcerto.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("ps.hasPermission('content:article:writer')")
    @PostMapping
    public ResponseResult<?> add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> pageArticle(Integer pageNum, Integer pageSize, PageArticleDto pageArticleDto){
        return articleService.pageArticle(pageNum, pageSize, pageArticleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getArticle(@PathVariable String id){
        return articleService.getArticle(id);
    }

    @PutMapping
    public ResponseResult<?> updateArticle(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> removeArticle(@PathVariable Long id){
        return articleService.removeArticle(id);
    }

}
