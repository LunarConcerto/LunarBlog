package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.mapper.RoleMapper;
import cafe.lunarconcerto.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.domain.entity.Role;
import cafe.lunarconcerto.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 18:41:46
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 如果是 返回集合中只需要有admin
        if (SecurityUtils.isAdmin()){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        // 否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId();
    }

}

