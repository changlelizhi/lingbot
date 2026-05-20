package space.changle.lingbot.user.controller;

import org.springframework.web.bind.annotation.*;
import space.changle.lingbot.user.service.UserService;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 20:35
 * @description 用户控制器
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
