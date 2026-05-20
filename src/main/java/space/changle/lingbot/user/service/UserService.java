package space.changle.lingbot.user.service;

import space.changle.lingbot.dto.CheckUserOutDto;
import space.changle.lingbot.dto.TmaLoginOutDto;


/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 21:54
 * @description 用户服务
 */
public interface UserService {

    TmaLoginOutDto tmaUserSignIn(String tmaInitData);

    CheckUserOutDto isUserRegistered(String initData);

    /*===============bot相关的服务===============*/



}
