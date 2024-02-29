package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.dto.TagDto;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.PageVo;
import cafe.lunarconcerto.domain.vo.TagVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cafe.lunarconcerto.domain.entity.Tag;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-12-29 15:59:12
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult<?> addTag(TagDto tagDto);

    ResponseResult<?> removeTag(Long id);


    ResponseResult<Tag> getTag(Long id);

    ResponseResult<?> updateTag(TagDto tagDto);

    List<TagVo> listAllTag();

}

