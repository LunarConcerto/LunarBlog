package cafe.lunarconcerto.handler.security;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LunarConcerto
 * @time 2023/12/24
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult<?> result ;

        if (e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        }else if (e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败.");
        }

        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }

}
