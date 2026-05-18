package space.changle.lingbot.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 21:52
 * @description 用户注册
 */
public record TmaUserSignInInDto(@NotBlank(message = "initData不能为空") String initData)  {

}
