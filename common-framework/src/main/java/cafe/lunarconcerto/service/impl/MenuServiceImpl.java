package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.mapper.MenuMapper;
import cafe.lunarconcerto.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.domain.entity.Menu;
import cafe.lunarconcerto.service.MenuService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 18:37:11
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员， 返回所有的权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, "C", "F");
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = list(wrapper);

            return menuList.stream()
                    .map(Menu::getPerms)
                    .toList();
        }

        // 否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long id) {
        MenuMapper menuMapper = getBaseMapper();
        // 是否是管理员
        List<Menu> menuList = null;
        if (SecurityUtils.isAdmin()){
            // 如果是 返回所有符合要求的menu
            menuList = menuMapper.selectAllRouterMenu();
        }else {
            // 否则 返回当前用户所具有的Menu
            menuList = menuMapper.selectRouterMenuTreeByUserId();
        }

        // 转化为树状返回
        return buildMenuTree(menuList, 0L);
    }

    private List<Menu> buildMenuTree(@NotNull List<Menu> menuList, long parentId) {
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menuList)))
                .toList();
    }

    private List<Menu> getChildren(Menu menu, @NotNull List<Menu> menuList) {
        return menuList.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menuList)))
                .collect(Collectors.toList());
    }

}

