package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.domain.entity.LoginUser;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.domain.vo.BlogUserLoginVo;
import cafe.lunarconcerto.domain.vo.UserInfoVo;
import cafe.lunarconcerto.service.BlogLoginService;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import cafe.lunarconcerto.utils.JwtUtil;
import cafe.lunarconcerto.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<?> login(User user) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        // 判断是否认证通过

        if (Objects.isNull(authentication)){
            throw new RuntimeException("用户名或者密码错误.");
        }

        // 获取User id 生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());

        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId, loginUser);

        // 把token 和 userinfo 封装并返回

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult<?> logout() {
        // 获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 获取userid
        Long userId = loginUser.getUser().getId();

        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }

}
