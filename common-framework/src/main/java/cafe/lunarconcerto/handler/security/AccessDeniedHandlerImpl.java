package cafe.lunarconcerto.handler.security;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult<?> result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }

}
