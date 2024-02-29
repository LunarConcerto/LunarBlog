package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.domain.vo.BlogUserLoginVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.service.BlogLoginService;
import cafe.lunarconcerto.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            // 提示 必须传用户名
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult<?> logout(){
        return blogLoginService.logout();
    }

}
