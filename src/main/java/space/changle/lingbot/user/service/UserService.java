package space.changle.lingbot.user.service;

import space.changle.lingbot.dto.TmaUserSignInOutDto;


/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 21:54
 * @description 用户服务
 */
public interface UserService {

    TmaUserSignInOutDto tmaUserSignIn(String tmaInitData);
}
