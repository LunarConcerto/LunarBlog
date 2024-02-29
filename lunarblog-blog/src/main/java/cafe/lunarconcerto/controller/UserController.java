package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.entity.User;
import cafe.lunarconcerto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LunarConcerto
 * @time 2023/12/25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult<?> userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult<?> updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody User user){
        return userService.register(user);
    }

}
