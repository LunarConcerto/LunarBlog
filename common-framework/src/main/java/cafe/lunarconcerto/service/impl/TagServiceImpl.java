package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.dto.TagDto;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.PageVo;
import cafe.lunarconcerto.domain.vo.TagVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.mapper.TagMapper;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.domain.entity.Tag;
import cafe.lunarconcerto.service.TagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 15:59:12
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, @NotNull TagDto tagDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagDto.getName()), Tag::getName, tagDto.getName());
        queryWrapper.like(StringUtils.hasText(tagDto.getRemark()), Tag::getRemark, tagDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page);

        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<?> addTag(@NotNull TagDto tagDto) {
        if (!StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.EMPTY_VALUE);
        }

        if (!StringUtils.hasText(tagDto.getRemark())){
            throw new SystemException(AppHttpCodeEnum.EMPTY_VALUE);
        }

        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        save(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> removeTag(Long id) {
        Tag tag = new Tag();

        tag.setId(id);
        tag.setDelFlag(SystemConstants.STATUS_DELETE);

        updateById(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Tag> getTag(Long id) {
        return ResponseResult.okResult(getById(id));
    }

    @Override
    public ResponseResult<?> updateTag(TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        return BeanCopyUtils.copyBeanList(list, TagVo.class);
    }


}

