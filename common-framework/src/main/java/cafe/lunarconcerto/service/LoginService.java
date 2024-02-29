package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
public interface LoginService {

    ResponseResult<?> login(User user);

    ResponseResult<?> logout();
}
