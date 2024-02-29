package cafe.lunarconcerto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cafe.lunarconcerto.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-29 18:37:10
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId();
}

