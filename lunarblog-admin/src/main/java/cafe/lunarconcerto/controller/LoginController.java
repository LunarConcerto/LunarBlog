package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.LoginUser;
import cafe.lunarconcerto.domain.entity.Menu;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.domain.vo.AdminUserInfoVo;
import cafe.lunarconcerto.domain.vo.RoutersVo;
import cafe.lunarconcerto.domain.vo.UserInfoVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.service.LoginService;
import cafe.lunarconcerto.service.MenuService;
import cafe.lunarconcerto.service.RoleService;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import cafe.lunarconcerto.utils.RedisCache;
import cafe.lunarconcerto.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult<?> login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult<?> logout(){
        return loginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        // 获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 根据用户ID查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        // 根据用户ID查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(loginUser.getUser().getId());

        // 封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
