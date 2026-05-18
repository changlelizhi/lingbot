package space.changle.lingbot.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.changle.lingbot.common.ApiResponse;
import space.changle.lingbot.dto.TmaUserSignInInDto;
import space.changle.lingbot.dto.TmaUserSignInOutDto;
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

    @PostMapping("/tma/signin")
    public ApiResponse<TmaUserSignInOutDto> signup(@RequestBody @Validated TmaUserSignInInDto inInDto) {
        TmaUserSignInOutDto tmaUserSignInOutDto = userService.tmaUserSignIn(inInDto.initData());
        return ApiResponse.success(tmaUserSignInOutDto);
    }
}
