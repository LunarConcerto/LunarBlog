package cafe.lunarconcerto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cafe.lunarconcerto.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-12-29 18:41:46
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

