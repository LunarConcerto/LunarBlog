package cafe.lunarconcerto.utils;

import cafe.lunarconcerto.domain.entity.LoginUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author LunarConcerto
 * @time 2023/12/25
 */
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @NotNull
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

}
