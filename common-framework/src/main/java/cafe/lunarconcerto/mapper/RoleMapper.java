package cafe.lunarconcerto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cafe.lunarconcerto.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-29 18:41:45
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId();

}

