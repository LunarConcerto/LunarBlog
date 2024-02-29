package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-04 09:19:09
 */
public interface UserService extends IService<User> {

    ResponseResult<?> userInfo();

    ResponseResult<?> updateUserInfo(User user);

    ResponseResult<?> register(User user);
}

