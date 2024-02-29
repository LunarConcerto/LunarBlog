package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.domain.vo.UserInfoVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.mapper.UserMapper;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import cafe.lunarconcerto.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cafe.lunarconcerto.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-04 09:19:09
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult<?> userInfo() {
        // 获取当前用户ID
        Long userId = SecurityUtils.getUserId();

        // 根据用户ID查询用户信息
        User user = getById(userId);

        // 封装乘 UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult<?> updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> register(@NotNull User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_IS_NULL);
        }

        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_IS_NULL);
        }

        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_IS_NULL);
        }

        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_IS_NULL);
        }

        // 对数据进行是否存在的判断
        if (isUsernameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        if (isNicknameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_IS_EXIST);
        }

        // 密码处理
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean isNicknameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean isUsernameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }


}

