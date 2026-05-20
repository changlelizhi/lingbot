package space.changle.lingbot.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.changle.lingbot.auth.jwt.JwtUtil;
import space.changle.lingbot.common.ApiResponse;
import space.changle.lingbot.dto.CheckUserOutDto;
import space.changle.lingbot.dto.TmaLoginInDto;
import space.changle.lingbot.dto.TmaLoginOutDto;
import space.changle.lingbot.user.service.UserService;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @time 23:40
 * @description
 */
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public AuthController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @PostMapping("/checkUser")
    public ApiResponse<CheckUserOutDto> checkUser(@RequestBody @Validated TmaLoginInDto tmaLoginInDto) {
        CheckUserOutDto checkUserOutDto = userService.isUserRegistered(tmaLoginInDto.initData());
        return ApiResponse.success(checkUserOutDto);
    }


    @PostMapping("/tmaLogin")
    public ApiResponse<TmaLoginOutDto> tmaLogin(@RequestBody @Validated TmaLoginInDto tmaLoginInDto) {
        TmaLoginOutDto tmaLoginOutDto = userService.tmaUserSignIn(tmaLoginInDto.initData());
        return ApiResponse.success(tmaLoginOutDto);
    }


}
