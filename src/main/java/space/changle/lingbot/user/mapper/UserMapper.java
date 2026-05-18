package space.changle.lingbot.user.mapper;

import space.changle.lingbot.user.entity.UserEntity;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/14
 * @time 22:30
 * @description
 */
public interface UserMapper {


    UserEntity selectByUserId(Long userId);


    void insertUser(UserEntity user);
}
