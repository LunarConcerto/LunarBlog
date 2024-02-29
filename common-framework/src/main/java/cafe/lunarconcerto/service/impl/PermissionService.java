package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        // 如果是超级管理员直接返回true
        if (SecurityUtils.isAdmin()){
            return true;
        }

        // 否则获取当前登录用户所具有的权限列表, 然后判断
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }

}
