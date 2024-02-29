package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.domain.entity.Link;
import cafe.lunarconcerto.service.LinkService;
import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.LinkVo;
import cafe.lunarconcerto.mapper.LinkMapper;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-05-04 08:45:36
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult<?> getAllLink() {
        // 查询所有审核通过的LINK
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);

        // 转换成VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        // 封装返回
        return ResponseResult.okResult(linkVos);
    }

}

