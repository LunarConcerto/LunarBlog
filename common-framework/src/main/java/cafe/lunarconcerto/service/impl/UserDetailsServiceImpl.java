package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.LoginUser;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.mapper.MenuMapper;
import cafe.lunarconcerto.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUserName, username);

        User user = userMapper.selectOne(queryWrapper);
        // 判断是否存在用户, 若不存在则抛出异常
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("不存在用户名:" + username);
        }

        // 查询权限信息封装
        // 如果是后台用户才需要查询权限
        if (SystemConstants.ADMIN.equals(user.getType())){
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }

        // 返回用户信息
        return new LoginUser(user);
    }

}
