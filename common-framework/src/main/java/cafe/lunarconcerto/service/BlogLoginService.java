package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.domain.vo.BlogUserLoginVo;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
public interface BlogLoginService {
    ResponseResult<?> login(User user);

    ResponseResult<?> logout();
}
