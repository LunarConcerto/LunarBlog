package cafe.lunarconcerto.filter;

import cafe.lunarconcerto.domain.entity.LoginUser;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.utils.JwtUtil;
import cafe.lunarconcerto.utils.RedisCache;
import cafe.lunarconcerto.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TOKEN权限认证
 * <p>
 * 用于某些登录才能访问的接口，验证其TOKEN是否合法
 * @author LunarConcerto
 * @time 2023/12/24
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的TOKEN
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 不需要登录的接口 直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析获取userId
        Claims claims = null;

        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时或者非法
            //响应前端 需要重新登录
            ResponseResult<?> result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        String userId = claims.getSubject();

        // 从redis中获取用户信息
        LoginUser user = redisCache.getCacheObject("bloglogin:" + userId);
        if (user == null){
            //响应前端 需要重新登录
            ResponseResult<?> result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
