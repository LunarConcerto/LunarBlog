package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.Link;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-05-04 08:45:35
 */
public interface LinkService extends IService<Link> {

    ResponseResult<?> getAllLink();

}

