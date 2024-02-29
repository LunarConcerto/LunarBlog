package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.dto.TagDto;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.Tag;
import cafe.lunarconcerto.domain.vo.PageVo;
import cafe.lunarconcerto.domain.vo.TagVo;
import cafe.lunarconcerto.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDto tagDto){
        return tagService.pageTagList(pageNum, pageSize, tagDto);
    }

    @PostMapping
    public ResponseResult<?> addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> removeTag(@PathVariable Long id){
        return tagService.removeTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<Tag> getTag(@PathVariable Long id){
        return tagService.getTag(id);
    }

    @PutMapping
    public ResponseResult<?> updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult<?> listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

}
